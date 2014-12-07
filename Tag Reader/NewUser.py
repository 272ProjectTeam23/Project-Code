#-------------------------------------------------------------------------------
# Name:        SocialServices: New User Tag Registration
# Purpose:      Once a tagNo is created, register it in the rfid database and
#               program it to an RFID key
#
# Author:      Stephen
#
# Created:     02/12/2014
# Copyright:   (c) Stephen 2014
# Licence:     to update with license of software below
#-------------------------------------------------------------------------------


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
    #initialize encryption
    PADDING = '{'
    BLOCK_SIZE = 32
    pad = lambda s: s + (BLOCK_SIZE - len(s) % BLOCK_SIZE) * PADDING
    #prepare crypto method
    EncodeAES = lambda c, s: base64.b64encode(c.encrypt(pad(s)))
    DecodeAES = lambda c, e: c.decrypt(base64.b64decode(e)).rstrip(PADDING)
    cipher = AES.new("horse elephant 9")
    #infinite loop
    while True:
        #get tag SN
        tagNo = int(raw_input("Tag Number from Registration: "))
        #get new rfid # from db
        rfid_db.create_index("rfidCode")
        rfidCode_cur = rfid_db.find().sort("rfidCode",pymongo.DESCENDING)
        if rfidCode_cur.count() == 0:
            rfidCode = '1'
        else:
            rfidCode = str(int(rfidCode_cur[0]["rfidCode"]) + 1)
        #check for tagNo in rfid db
        rfid_db.create_index("tagNo")
        tagNo_cur = rfid_db.find({"tagNo":tagNo})
        if tagNo_cur.count() > 0:
            #if tagNo in rfid db, update with rfid#
            rfid_db.update({"tagNo":tagNo},{'tagNo':tagNo,'rfidCode':rfidCode})
        else:
            #if tagNo not in rfid db, insert rfid# with tagNo
            #figure out next _id
            rfid_db.create_index("_id")
            id_cur = rfid_db.find()
            id_cur.sort("_id",pymongo.DESCENDING)
            id_cur[0]["_id"]
            #insert into db with next id, next rfidCode, and tagNo entered
            rfid_db.insert({'_id':id_cur[0]["_id"]+1,'tagNo':tagNo,'rfidCode':rfidCode})
        #encrypt rfid#
        encoded = EncodeAES(cipher,rfidCode)
        print(encoded)
        #get code ready to program
        sp = nfc.ndef.TextRecord(encoded)
        #program rfid# to tag
        print("Scan tag now")
        tag = clf.connect(rdwr={'on-connect':connect})
        tag.ndef.message = nfc.ndef.Message(sp)
        #provide feedback
        if 'q' == raw_input("type q to quit, c to continue"):
            #cleanup
            clf.close()
            client.close()
            break
    pass


if __name__ == '__main__':
    main()
