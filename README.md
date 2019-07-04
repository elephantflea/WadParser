# WadParser
A helper library to parse WAD files and extract lumps contained in WAD files.

Based on:
* https://doomwiki.org/wiki/WAD
* http://www.gamers.org/dEngine/quake/spec/quake-spec34/qkspec_7.htm
* http://hlbsp.sourceforge.net/index.php?content=waddef

## Supported Formats
* IWAD/PWAD
  * Doom, Heretic, Hexen and Strife
* WAD2
  * Quake
* WAD3
  * Half-Life, Opposing Force, Blue Shift
## Usage
```java
import nz.doom.wadparser.containers.Lump;
import nz.doom.wadparser.containers.WAD;
import nz.doom.wadparser.parser.WadParser;

public class ListLumps {
    public static void main(String[] args) throws Exception {
        if(args == null || args.length != 1){
            System.err.println("WAD file required");
            System.exit(2);
        }
        
        Path wadPath = Paths.get(args[0]);
        
        WAD wad = WadParser.parse(wadPath);
        
        for(Lump lump : wad.getLumps()){
            System.out.println(lump.getName());
        }
    }    
}
```
### See also
Also available for C# .NET Core https://github.com/elephantflea/WadParser-dotnet