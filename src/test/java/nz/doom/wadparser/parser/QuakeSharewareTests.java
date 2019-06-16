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

public class QuakeSharewareTests {

    private static WAD quakeShareware;

    @BeforeAll
    public static void loadWad() throws URISyntaxException, IOException, WadParseException {
        Path quakeSharewarePath = Paths.get(QuakeSharewareTests.class.getResource("/wads/quake-gfx.wad").toURI());
        quakeShareware = WadParser.parse(quakeSharewarePath);
    }

    @Test
    public void wadAttribute(){
        assertEquals(WadType.WAD2, quakeShareware.getType());
        assertEquals(112828, quakeShareware.getSize());
        assertEquals(163, quakeShareware.getLumpCount());
        assertEquals("quake-gfx.wad", quakeShareware.getFilename());
    }

    @Test
    public void getLumpByName(){
        Lump sigilLump = quakeShareware.getLump("SB_SIGIL4");

        assertNotNull(sigilLump);
        assertEquals("SB_SIGIL4",sigilLump.getName());
        assertEquals(136,sigilLump.getSize());
        assertEquals(83,sigilLump.getPosition());
        assertEquals(0x42,sigilLump.getType());
        assertFalse(sigilLump.isCompressed());

        byte[] b = sigilLump.getBytes();

        assertEquals(0x08,b[0]);
        assertEquals(0x11,b[b.length-1]);
        assertEquals(-82,b[38]);
    }

    @Test
    public void getLumpsByName(){
        List<Lump> lumps = quakeShareware.getLumps("FACE1");

        assertNotNull(lumps);
        assertEquals(1,lumps.size());
    }

    @Test
    public void corruptLumpCheck(){
        List<Lump> lumps = quakeShareware.getLumps();

        assertNotNull(lumps);

        for(Lump lump : lumps){
            assertFalse(lump.isCorrupt());
        }
    }

}
