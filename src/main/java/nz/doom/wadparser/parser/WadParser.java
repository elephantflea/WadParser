package nz.doom.wadparser.parser;

import nz.doom.wadparser.containers.Lump;
import nz.doom.wadparser.containers.WAD;
import nz.doom.wadparser.containers.WadType;
import nz.doom.wadparser.identifier.WadIdentifier;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.READ;

public class WadParser {

    public static final int WAD_DIRECTORY_SIZE = 16;
    public static final int WAD2_DIRECTORY_SIZE = 32;

    public static final int WAD_NAME_OFFSET = 8;
    public static final int WAD_NAME_SIZE = 8;

    public static final int WAD2_NAME_OFFSET = 16;
    public static final int WAD2_NAME_SIZE = 16;


    /**
     * TODO: this
     * @param wadPath
     * @return
     * @throws IOException
     * @throws WadParseException
     */
    public static WAD parse(Path wadPath) throws IOException, WadParseException {

        if(wadPath == null){
            throw new WadParseException("WAD path is null");
        }

        WAD wad = new WAD(wadPath);

        int dirSize = WAD_DIRECTORY_SIZE;
        int nameOffset = WAD_NAME_OFFSET;
        int nameSize = WAD_NAME_SIZE;

        try (FileChannel fileChannel = (FileChannel.open(wadPath,READ))){

            WadType wadType = WadIdentifier.getWadType(wadPath);

            switch(wadType){
                case WAD2:
                case WAD3:
                    dirSize = WAD2_DIRECTORY_SIZE;
                    nameOffset = WAD2_NAME_OFFSET;
                    nameSize = WAD2_NAME_SIZE;
                    break;
                case UNKNOWN:
                    throw new WadParseException("WAD type could not be determined");
            }

            int lumpCount = getIntegerFromByteArray(getByteContent(fileChannel,4,4).array(),0);

            if(lumpCount > 65536){
                throw new WadParseException(String.format("Too many lumps listed in header: %d",lumpCount));
            }

            int directoryPosition = getIntegerFromByteArray(getByteContent(fileChannel,8,4).array(),0);

            if(directoryPosition > wad.getSize()){
                throw new WadParseException(String.format("Directory offset is larger than WAD file size: %d",directoryPosition));
            }

            if((directoryPosition + (dirSize * lumpCount))> wad.getSize()) {
                throw new WadParseException("Directory goes off the end of the WAD" );
            }

            byte[] directoryBytes = getByteContent(fileChannel,directoryPosition,lumpCount*dirSize).array();

            int lumpEntryNumber = -1;
            for(int i = 0;i < directoryBytes.length;i+=dirSize){
                int lumpOffset = getIntegerFromByteArray(directoryBytes,i);
                lumpEntryNumber++;

                String lumpName = new String(directoryBytes,i+nameOffset,nameSize, StandardCharsets.US_ASCII);
                int nullOffset = lumpName.indexOf("\0");
                if(nullOffset > -1){
                    lumpName = lumpName.substring(0,nullOffset);
                }

                lumpName = lumpName.trim();
                Lump lump = new Lump();

                lump.setName(lumpName);
                lump.setOffset(lumpOffset);
                lump.setPosition(lumpEntryNumber);
                lump.setSize(getIntegerFromByteArray(directoryBytes,i+4));

                if(WadType.WAD2.equals(wadType) || WadType.WAD3.equals(wadType)){
                    lump.setType(getShortFromByteArray(directoryBytes,i+12));
                    lump.setCompression(getShortFromByteArray(directoryBytes,i+13) == 1);
                }

                if(lump.getSize() == 0){
                    lump.setBytes(new byte[]{});
                }else{
                    byte[] lumpBytes = getByteContent(fileChannel, lumpOffset, lump.getSize()).array();
                    lump.setBytes(lumpBytes);
                }

                wad.addLump(lump);
            }
        }

        return wad;
    }

    /**
     * Returns any array of bytes from the file channel
     * @param fileChannel The file change to read the bytes from
     * @param position The position to read the bytes from
     * @param length How many bytes to read
     * @return The array of bytes read
     * @throws IOException
     */
    private static ByteBuffer getByteContent(final FileChannel fileChannel, final long position, final int length) throws IOException {
        final ByteBuffer buffer = ByteBuffer.allocate(length);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        fileChannel.position(position);

        int bytesRead;
        do {
            bytesRead = fileChannel.read(buffer);
        } while (bytesRead != -1 && buffer.hasRemaining());

        return buffer;
    }

    /**
     * TODO: This
     * @param byteArray
     * @param start
     * @return
     */
    private static int getIntegerFromByteArray(final byte[] byteArray,final int start){
        byte[] newByte = new byte[4];

        System.arraycopy(byteArray,start,newByte,0,4);

        final ByteBuffer byteBuffer = ByteBuffer.wrap(newByte);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return byteBuffer.getInt();
    }

    /**
     * TODO: This
     * @param byteArray
     * @param start
     * @return
     */
    private static short getShortFromByteArray(final byte[] byteArray,final int start){
        byte[] newByte = new byte[4];

        System.arraycopy(byteArray,start,newByte,0,4);

        final ByteBuffer byteBuffer = ByteBuffer.wrap(newByte);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return byteBuffer.getShort();
    }
}
