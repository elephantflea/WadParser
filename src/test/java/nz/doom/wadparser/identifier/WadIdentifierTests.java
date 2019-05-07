package nz.doom.wadparser.identifier;

import nz.doom.wadparser.containers.WadType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WadIdentifierTests {

    @Test
    public void pwadTest() throws URISyntaxException, IOException {
        Path testFile = Paths.get(getClass().getResource("/wadtypes/PWAD.txt").toURI());
        WadType type = WadIdentifier.getWadType(testFile);
        assertEquals(WadType.PWAD,type);
    }

    @Test
    public void iwadTest() throws URISyntaxException, IOException {
        Path testFile = Paths.get(getClass().getResource("/wadtypes/IWAD.txt").toURI());
        WadType type = WadIdentifier.getWadType(testFile);
        assertEquals(WadType.IWAD,type);
    }

    @Test
    public void wad2Test() throws URISyntaxException, IOException {
        Path testFile = Paths.get(getClass().getResource("/wadtypes/WAD2.txt").toURI());
        WadType type = WadIdentifier.getWadType(testFile);
        assertEquals(WadType.WAD2,type);
    }

    @Test
    public void wad3Test() throws URISyntaxException, IOException {
        Path testFile = Paths.get(getClass().getResource("/wadtypes/WAD3.txt").toURI());
        WadType type = WadIdentifier.getWadType(testFile);
        assertEquals(WadType.WAD3,type);
    }

    @Test
    public void unknownTest() throws URISyntaxException, IOException {
        Path testFile = Paths.get(getClass().getResource("/wadtypes/UNKNOWN.txt").toURI());
        WadType type = WadIdentifier.getWadType(testFile);
        assertEquals(WadType.UNKNOWN,type);
    }

    @Test
    public void notEnoughBytes() {
        byte[] bytes = "123".getBytes();
        WadType type = WadIdentifier.getWadType(bytes);
        assertEquals(WadType.UNKNOWN,type);
    }
}
