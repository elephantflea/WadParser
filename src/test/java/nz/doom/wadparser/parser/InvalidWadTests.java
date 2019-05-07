package nz.doom.wadparser.parser;

import nz.doom.wadparser.containers.WAD;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class InvalidWadTests {


    @Test
    public void tooManyLumps() throws URISyntaxException {
        Path bigTestFile = Paths.get(getClass().getResource("/wads/TooManyLumps.wad").toURI());

        try {
            WadParser.parse(bigTestFile);
        } catch (WadParseException e) {
            assertEquals("Too many lumps listed in header: 65537",e.getMessage());
            return;
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        fail();
    }

    @Test
    public void badDirectoryOffset() throws URISyntaxException {
        Path bigTestFile = Paths.get(getClass().getResource("/wads/BadDirectoryOffset.wad").toURI());

        try {
            WadParser.parse(bigTestFile);
        } catch (WadParseException e) {
            assertEquals("Directory offset is larger than WAD file size: 268439552",e.getMessage());
            return;
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        fail();
    }

    @Test
    public void badDirectoryLength() throws URISyntaxException {
        Path bigTestFile = Paths.get(getClass().getResource("/wads/BadDirectoryLength.wad").toURI());

        try {
            WadParser.parse(bigTestFile);
        } catch (WadParseException e) {
            assertEquals("Directory goes off the end of the WAD",e.getMessage());
            return;
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        fail();
    }

    @Test
    public void nullWad() {
        try {
            WadParser.parse(null);
        } catch (WadParseException e) {
            assertTrue(e.getMessage().contains("WAD path is null"));
            return;
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        fail();
    }

}
