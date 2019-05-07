package nz.doom.wadparser.containers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class WAD {

    private int wadSize;
    private Path wadPath;
    private List<Lump> lumps;
    private WadType wadType = WadType.UNKNOWN;

    /**
     * Initialise this WAD with the given Path. Will automatically set the file size. The Path must be readable
     * @throws IOException If the Path cannot be read
     * @param wadFile The Path of the WAD file
     */
    public WAD(Path wadFile) throws IOException {
        wadSize = (int)Files.size(wadFile);
        this.wadPath = wadFile;
        lumps = new ArrayList<>();
    }

    /**
     * Initialise this WAD with the given bytes. Will automatically set the file size
     * @param wadBytes The bytes of the WAD file
     */
    public WAD(byte[] wadBytes){
        wadSize = wadBytes.length;
        this.wadPath = null;
        lumps = new ArrayList<>();
    }

    /**
     * Add a lump to this WAD
     * @param lump Lump to add to the WAD
     */
    public void addLump(Lump lump){
        lumps.add(lump);
    }

    /**
     * Add a lump to this WAD file at the given position. Will update all the lump positions.
     * @param lump The lump to add
     * @param position The position to add the lump at
     */
    public void addLump(Lump lump, int position){
        lumps.add(position,lump);

        int p = 0;
        for(Lump l : lumps){
            l.setPosition(p);
            p++;
        }
    }

    /**
     * Set what type of WAD this is
     * @param wadType The WAD type
     */
    public void setWadType(WadType wadType){
        this.wadType = wadType;
    }

    /**
     * The number of lumps found in this WAD
     * @return The number of lumps
     */
    public int getLumpCount() {
        return lumps.size();
    }

    /**
     * What type of WAD this is. Defaults to {@link WadType#UNKNOWN}.
     * @return The WAD type
     */
    public WadType getType() {
        return wadType;
    }

    /**
     * The size of this WAD in bytes
     * @return The WAD size in bytes
     */
    public int getSize() {
        return wadSize;
    }

    /**
     * The {@link Path} that represents this WAD
     * @return The WAD Path
     */
    public Path getPath() {
        return wadPath;
    }

    /**
     * The filename of this WAD
     * @return The WAD filename. Can be null if no Path was set
     */
    public String getFilename() {
        if(wadPath == null){
            return null;
        }
        return wadPath.getFileName().toString();
    }

    /**
     * All the lumps in this WAD
     * @return All the lumps in this WAD
     */
    public List<Lump> getLumps() {
        return lumps;
    }

    /**
     * Returns all lumps in this WAD with the given name.
     * @param name The name of the lumps to find. Case sensitive
     * @return A list of all the lumps with the given name or an empty list
     */
    public List<Lump> getLumps(String name) {
        List<Lump> namedLumps = new ArrayList<>();

        if(name == null || name.trim().isEmpty()){
            return namedLumps;
        }

        for(Lump lump : lumps){
            if(name.equals(lump.getName())){
                namedLumps.add(lump);
            }
        }

        return namedLumps;
    }

    /**
     * Retrieve a lump at the given position
     * @param position The position of the lump to retrieve
     * @return The lump at the given position or <code>null</code>
     */
    public Lump getLump(int position) {
        if(position < 0 || position >= lumps.size()){
            return null;
        }
        return lumps.get(position);
    }

    /**
     * Retrieve the first lump found with the given name starting at position 0
     * @param name The name of the lump to find. Case sensitive
     * @return The first lump with the given name or <code>null</code>
     */
    public Lump getLump(String name) {
        if(name == null || name.trim().isEmpty()){
            return null;
        }
        for(Lump lump : lumps){
            if(name.equals(lump.getName())){
                return lump;
            }
        }
        return null;
    }
}
