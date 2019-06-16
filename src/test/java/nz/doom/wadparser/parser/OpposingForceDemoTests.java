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

public class OpposingForceDemoTests {

    private static WAD oppposingForceDemo;

    @BeforeAll
    public static void loadWad() throws URISyntaxException, IOException, WadParseException {
        Path oppposingForceDemoPath = Paths.get(OpposingForceDemoTests.class.getResource("/wads/opposing-force-spraypaint.wad").toURI());
        oppposingForceDemo = WadParser.parse(oppposingForceDemoPath);
    }

    @Test
    public void wadAttribute(){
        assertEquals(WadType.WAD3, oppposingForceDemo.getType());
        assertEquals(87988, oppposingForceDemo.getSize());
        assertEquals(14, oppposingForceDemo.getLumpCount());
        assertEquals("opposing-force-spraypaint.wad", oppposingForceDemo.getFilename());
    }

    @Test
    public void getLumpByName(){
        Lump lambdaLump = oppposingForceDemo.getLump("LAMBDA");

        assertNotNull(lambdaLump);
        assertEquals("LAMBDA",lambdaLump.getName());
        assertEquals(6252,lambdaLump.getSize());
        assertEquals(7,lambdaLump.getPosition());
        assertEquals(0x43,lambdaLump.getType());
        assertFalse(lambdaLump.isCompressed());

        byte[] b = lambdaLump.getBytes();

        assertEquals(0x6C,b[0]);
        assertEquals(0x00,b[b.length-1]);
        assertEquals(0x29,b[5607]);
    }

    @Test
    public void getLumpsByName(){
        List<Lump> lumps = oppposingForceDemo.getLumps("LAMBDA");

        assertNotNull(lumps);
        assertEquals(1,lumps.size());
    }

    @Test
    public void corruptLumpCheck(){
        List<Lump> lumps = oppposingForceDemo.getLumps();

        assertNotNull(lumps);

        for(Lump lump : lumps){
            assertFalse(lump.isCorrupt());
        }
    }

}
