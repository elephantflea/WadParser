package nz.doom.wadparser.parser;

import nz.doom.wadparser.containers.Lump;
import nz.doom.wadparser.containers.WAD;
import nz.doom.wadparser.containers.WadType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DoomSharewareTests {

    private static WAD doom1Shareware;

    @BeforeAll
    public static void loadWad() throws URISyntaxException, IOException, WadParseException {
        Path doom1SharewarePath = Paths.get(DoomSharewareTests.class.getResource("/wads/doom1.wad").toURI());
        doom1Shareware = WadParser.parse(doom1SharewarePath);
    }

    @Test
    public void wadAttribute(){
        assertEquals(WadType.IWAD,doom1Shareware.getType());
        assertEquals(4274218,doom1Shareware.getSize());
        assertEquals(1270,doom1Shareware.getLumpCount());
        assertEquals("doom1.wad",doom1Shareware.getFilename());
    }

    @Test
    public void getLumpByName(){
        Lump endoomLump = doom1Shareware.getLump("ENDOOM");

        assertNotNull(endoomLump);
        assertEquals("ENDOOM",endoomLump.getName());
        assertEquals(4000,endoomLump.getSize());
        assertEquals(2,endoomLump.getPosition());
        assertEquals(-1,endoomLump.getType());
        assertFalse(endoomLump.isCompressed());

        byte[] b = endoomLump.getBytes();

        assertEquals(0x20,b[0]);
        assertEquals(0x07,b[b.length-1]);
        assertEquals(0x4F,b[3381]);
    }

    @Test
    public void getLumpsByName(){
        List<Lump> lumps = doom1Shareware.getLumps("THINGS");

        assertNotNull(lumps);
        assertEquals(9,lumps.size());
    }

}
