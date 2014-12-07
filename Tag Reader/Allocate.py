#-------------------------------------------------------------------------------
# Name:        SocialServices: Bed Allocation
# Purpose:      Allocates a bed in the beds database, and updates it in the user database
#
# Author:      Stephen
#
# Created:     02/12/2014
# Copyright:   (c) Stephen 2014
# Licence:     to update with license of software below
#-------------------------------------------------------------------------------

import time
import nfc
import pymongo
import sys
import base64
from Crypto.Cipher import AES

def connect(tag):
    pass

def main():

#initialize nfc reader
    clf = nfc.ContactlessFrontend()
    OpenError = clf.open("usb:000:001")
    if not(OpenError):
        print('Could not initialize reader')
        sys.exit()
#initialize database
    client = pymongo.MongoClient('mongodb://raina:raina@ds053370.mongolab.com:53370/socialservicedb')
    if not(client):
        print('database connection error')
        sys.exit()
    db = client.get_default_database()
    if not(db):
        print('database not found')
        sys.exit()
    rfid_db = db.rfid
    if not(rfid_db):
        print('rfid collection not found')
        sys.exit()
    beds_db = db.beds
    if not(beds_db):
        print('beds collection not found')
        sys.exit()
    users_db = db.users
    if not(users_db):
        print('users collection not found')
        sys.exit()
    #initialize encryption
    PADDING = '{'
    BLOCK_SIZE = 32
    pad = lambda s: s + (BLOCK_SIZE - len(s) % BLOCK_SIZE) * PADDING
    #prepare crypto method
    EncodeAES = lambda c, s: base64.b64encode(c.encrypt(pad(s)))
    DecodeAES = lambda c, e: c.decrypt(base64.b64decode(e)).rstrip(PADDING)
    cipher = AES.new("horse elephant 9")
    #infinite loop. Should never finish, the application is meant to be always on
    while True:
        #scan rfid tag
        print("Scan tag now")
        tag = clf.connect(rdwr={'on-connect':connect})
        #handle error if data length is not right to decode
        if 44 != len(tag.ndef.message[0].data[3:]):
            #deny access
            print("tag is programmed incorrectly, please see administrator")
            #wait 5 sec
            time.sleep(5)
            #continue loop
            continue
        #decrypt rfidCode
        rfidCode = DecodeAES(cipher,tag.ndef.message[0].data[3:])
        rfid_db.create_index("rfidCode")
        rfidCode_cur = rfid_db.find({"rfidCode":rfidCode})
        #if rfidCode doesn't exist
        if 0 == rfidCode_cur.count():
            #deny access
            print("tag is not in the database, please see administrator")
            #wait 5 sec
            time.sleep(5)
            #continue loop
            continue
        #get tagNo
        tagNo = rfidCode_cur[0]["tagNo"]
        users_db.create_index("tagNo")
        tagNo_cur = users_db.find({"tagNo":tagNo})
        #if tagNo is not associated with a user
        if 0 == tagNo_cur.count():
            #deny access
            print("tag is not associated with a user, please see administrator")
            #wait 5 sec
            time.sleep(5)
            #continue loop
            continue
        #find assigned bed
        bedId = tagNo_cur[0]["bedId"]
        #if bed != 0
        if 0 != bedId:
            #display assigned bed#
            print("You are already registered to bed " + str(bedId) + ", sweet dreams!")
            #wait 5 sec
            time.sleep(5)
            #continue loop
            continue
        #(bed == 0)
        #find available beds, sort by #
        beds_db.create_index("status")
        beds_db.create_index("_id")
        beds_cur = beds_db.find({"status":"available"}).sort("_id",pymongo.ASCENDING)
        #if no beds available
        if 0 == beds_cur.count():
            #deny access
            print("Sorry, there are no beds currently available")
            #wait 5 sec
            time.sleep(5)
            #continue loop
            continue
        #(beds available)
        #book first available bed
        bedId = beds_cur[0]["_id"]
        beds_db.update({"_id":bedId},{'_id':bedId,'status':"occupied"})
        #assign bed to user
        user_to_upd = tagNo_cur[0].copy()
        user_to_upd["bedId"] = bedId
        users_db.update({"_id":user_to_upd["_id"]},user_to_upd)
        #display assigned bed#
        print("You are now registered to bed " + str(bedId) + ", sweet dreams!")
        #wait 5 sec
        time.sleep(5)
        #continue loop
        continue
    pass


if __name__ == '__main__':
    main()
