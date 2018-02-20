/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketfileexchange.fileexchange;

/**
 *
 * @author Champ
 * 
 * This class contains header for the file sharing
 * 
 */
public class FileHeader {

    public static final int FILE_SIZE_HEADER_LENGTH = 8;
    public static final int FILE_ID_HEADER_LENGTH = 20;
    public static final int FILE_NAME_HEADER_LENGTH = 100;

    public String fileId;
    public long fileSize;
    public String fileName;

    public byte headerBytes[];

    public FileHeader() {

    }

    /**
     * this constructor will parse the headers provided
     * RuntimeExceptions are possible
     * @param headerBytes 
     */
    public FileHeader(byte headerBytes[]) {
        this.headerBytes = headerBytes;
        processByteHeader();
    }

    /**
     * process the headers which were passed through constructor
     */
    private void processByteHeader() {

        System.out.println("Total Header Size: " + headerBytes.length);

        byte[] fileSizeBytes = new byte[FILE_SIZE_HEADER_LENGTH];
        System.arraycopy(headerBytes, 0, fileSizeBytes, 0, FILE_SIZE_HEADER_LENGTH);
        fileSize = Utils.bytesToLong(fileSizeBytes);

        byte[] imeiBytes = new byte[FILE_ID_HEADER_LENGTH];
        System.arraycopy(headerBytes, FILE_SIZE_HEADER_LENGTH, imeiBytes, 0, FILE_ID_HEADER_LENGTH);
        fileId = new String(imeiBytes);

        byte[] nameBytes = new byte[FILE_NAME_HEADER_LENGTH];
        System.arraycopy(headerBytes, FILE_SIZE_HEADER_LENGTH + FILE_ID_HEADER_LENGTH, nameBytes, 0, FILE_NAME_HEADER_LENGTH);
        fileName = new String(nameBytes);
        System.out.println("Processed name: " + fileName);
        int index = fileName.indexOf('0');
        if (index > 0) {
            // strip the padded characters
            fileName = fileName.substring(0, fileName.indexOf('0'));
        }
    }

    /**
     * get the header bytes if all the params have been initialised
     * @return 
     */
    public byte[] getHeaderBytes() {
        byte[] headerBytes = new byte[getTotalHeaderLength()];

        byte[] fileSizeBytes = Utils.longToBytes(fileSize);
        System.out.println("File Size Bytes Length: " + fileSizeBytes.length);
        byte[] imeiBytes = getFileIdBytesWithPadding();
        System.out.println("IMEI Bytes Length: " + imeiBytes.length);
        byte[] nameBytes = getNameBytesWithPadding();
        System.out.println("Name Bytes Length: " + nameBytes.length);

        System.arraycopy(fileSizeBytes, 0, headerBytes, 0, fileSizeBytes.length);
        System.arraycopy(imeiBytes, 0, headerBytes, FILE_SIZE_HEADER_LENGTH, imeiBytes.length);
        System.arraycopy(nameBytes, 0, headerBytes, FILE_SIZE_HEADER_LENGTH + FILE_ID_HEADER_LENGTH, nameBytes.length);

        return headerBytes;
    }

    /**
     * Total header size of IMEI is 20. If IMEI is less then 20, then we have to
     * add leading zeros in it. This function does all this for us
     *
     * @return
     */
    private byte[] getFileIdBytesWithPadding() {
        // main imeiHeader which will be returned
        byte[] fileIdHeader = new byte[FILE_ID_HEADER_LENGTH];
        // imeiBytes of the FileId
        byte[] fileIdBytes = fileId.getBytes();
        // lenght of imeiBytes
        int imeiBytesLength = fileIdBytes.length;
        // no of leading zeros we have to add
        int noOfPaddedZeros = 0;
        // if imeiBytes is less then the header
        if (imeiBytesLength < FILE_ID_HEADER_LENGTH) {
            noOfPaddedZeros = FILE_ID_HEADER_LENGTH - imeiBytesLength;
            for (int i = 0; i < noOfPaddedZeros; i++) {
                fileIdHeader[i] = 0;
            }
        }
        System.arraycopy(fileIdBytes, 0, fileIdHeader, noOfPaddedZeros, imeiBytesLength);
        return fileIdHeader;
    }

    private byte[] getNameBytesWithPadding() {
        byte[] nameHeader = new byte[FILE_NAME_HEADER_LENGTH];

        byte[] nameBytes = fileName.getBytes();

        int nameBytesLength = nameBytes.length;
        /**
         * write name bytes into header, if name length is less then name header
         * length, then write name length header, otherwise skip last one
         */
        System.arraycopy(nameBytes, 0, nameHeader, 0,
                nameBytesLength < FILE_NAME_HEADER_LENGTH ? nameBytesLength : FILE_NAME_HEADER_LENGTH);
        for (int i = nameBytesLength; i < FILE_NAME_HEADER_LENGTH; i++) {
            nameHeader[i] = '0';
        }

        return nameHeader;
    }

    /**
     * get the total length of header
     *
     * @return size in integer
     */
    public int getTotalHeaderLength() {
        return FILE_SIZE_HEADER_LENGTH
                + FILE_ID_HEADER_LENGTH
                + FILE_NAME_HEADER_LENGTH;
    }
}
