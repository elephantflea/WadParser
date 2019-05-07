package nz.doom.wadparser.identifier;

import nz.doom.wadparser.containers.WadType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class WadIdentifier {

    public static final byte[] MAGIC_IWAD = {0x49, 0x57, 0x41, 0x44};
    public static final byte[] MAGIC_PWAD = {0x50, 0x57, 0x41, 0x44};
    public static final byte[] MAGIC_WAD2 = {0x57, 0x41, 0x44, 0x32};
    public static final byte[] MAGIC_WAD3 = {0x57, 0x41, 0x44, 0x33};

    /**
     * Will return the {@link WadType} of the given WAD path
     * @param wadPath The WAD file to determine the {@link WadType}
     * @return The WadType of the given <code>wadPath</code>. {@link WadType#UNKNOWN} if the type cannot be determined
     * @throws IOException If the bytes of <code>wadPath</code> cannot be read
     */
    public static WadType getWadType(final Path wadPath) throws IOException {
        return getWadType(Files.readAllBytes(wadPath));
    }

    /**
     * Will return the {@link WadType} of the given WAD bytes
     * @param wadBytes The WAD bytes to determine the {@link WadType}
     * @return The WadType of the given <code>wadPath</code>. {@link WadType#UNKNOWN} if the type cannot be determined
     */
    public static WadType getWadType(final byte[] wadBytes){
        if(wadBytes.length < 4){
           return WadType.UNKNOWN;
        }

        byte[] magicBytes = new byte[4];

        System.arraycopy(wadBytes,0,magicBytes,0,4);

        if(Arrays.equals(magicBytes,MAGIC_IWAD)){
            return WadType.IWAD;
        }
        if(Arrays.equals(magicBytes,MAGIC_PWAD)){
            return WadType.PWAD;
        }
        if(Arrays.equals(magicBytes,MAGIC_WAD2)){
            return WadType.WAD2;
        }
        if(Arrays.equals(magicBytes,MAGIC_WAD3)){
            return WadType.WAD3;
        }

        return WadType.UNKNOWN;
    }
}
