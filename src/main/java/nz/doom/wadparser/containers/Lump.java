package nz.doom.wadparser.containers;

import java.util.Objects;

public class Lump implements Comparable<Lump> {

    private int size = -1, offset = -1, position = -1, lumpType = -1;
    private String name = null;
    private byte[] bytes = null;
    private boolean isCompressed = false;
    private boolean isCorrupt = false;

    /**
     * The size of this lump in bytes
     * @return The size of this lump in bytes
     */
    public int getSize() {
        return size;
    }

    /**
     * Set the size of this lump in bytes
     * @param size The size of the lump in bytes
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Is this lump corrupt somehow
     * @return corrupt or not
     */
    public boolean isCorrupt(){
        return isCorrupt;
    }

    /**
     * Set whether or not this lump is corrupt
     * @param corrupt The lumps corrupt status
     */
    public void setCorrupt(boolean corrupt){
        this.isCorrupt = corrupt;
    }

    /**
     * The byte offset of this lump within the WAD
     * @return The byte offset of this lump
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Set the byte offset of this lump in the WAD
     * @param offset The byte offset
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * The name of this lump. If the lump's name is corrupt then the name will be set to
     * <code><CORRUPT NAME></code>
     * @return The name of this lump
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of this lump
     * @param name The name of this lump
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns this lumps bytes
     * @return The lump bytes
     */
    public byte[] getBytes() {
        return bytes;
    }

    /**
     * Set this lumps bytes
     * @param bytes The lumps bytes
     */
    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     * Alias of {@link Lump#getName()}
     * @return The lump name
     */
    public String toString() {
        return this.name;
    }

    /**
     * Compare this lump to another. Does not do a comparison of the lump bytes
     *
     * @param o object to compare this lump to
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lump lump = (Lump) o;
        return size == lump.size &&
                offset == lump.offset &&
                Objects.equals(name, lump.name);
    }


    @Override
    public int hashCode() {
        return Objects.hash(size, offset, name);
    }

    /**
     * The position of the lump in the WAD. Not the byte offset. 0 based
     * @see Lump#getOffset()
     * @return The lump offset
     */
    public int getPosition() {
        return position;
    }

    /**
     * Set the position of this lump in the WAD. This is not the byte offset
     * @see Lump#setOffset(int)
     * @param position The position of the lump in the WAD
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Set what type of lump this is. Only applicable to {@link WadType#WAD2} and {@link WadType#WAD3}.
     * <br/>
     * Only known types are:<br/>
     * WAD2:
     * <ul>
     *     <li>0x40=	'@'=	Color Palette</li>
     *     <li>0x42=	'B'=	Pictures for status bar</li>
     *     <li>0x44=	'D'=	Used to be Mip Texture</li>
     *     <li>0x45=	'E'=	Console picture (flat)</li>
     * </ul>
     * WAD3:
     * <ul>
     *     <li>0x44=	'D'=	miptex</li>
     *     <li>0x42=	'B'=	qpic</li>
     *     <li>0x45=	'E'=	font</li>
     * </ul>
     *
     * @param lumpType The lump type
     */
    public void setType(int lumpType) {
        this.lumpType = lumpType;
    }

    /**
     * Is this lump compressed. Only applicable to {@link WadType#WAD2} and {@link WadType#WAD3}.
     *
     * @param lumpCompression
     */
    public void setCompression(boolean lumpCompression) {
        this.isCompressed = lumpCompression;
    }

    /**
     * Get what type of lump this is. Only applicable to {@link WadType#WAD2} and {@link WadType#WAD3}.
     * <br/>
     * Only known types are:<br/>
     * WAD2:
     * <ul>
     *     <li>0x40=	'@'=	Color Palette</li>
     *     <li>0x42=	'B'=	Pictures for status bar</li>
     *     <li>0x44=	'D'=	Used to be Mip Texture</li>
     *     <li>0x45=	'E'=	Console picture (flat)</li>
     * </ul>
     * WAD3:
     * <ul>
     *     <li>0x44=	'D'=	miptex</li>
     *     <li>0x42=	'B'=	qpic</li>
     *     <li>0x45=	'E'=	font</li>
     * </ul>
     *
     * @return The lump type
     */
    public int getType() {
        return lumpType;
    }

    /**
     * Is this lump compressed. Only applicable to {@link WadType#WAD2} and {@link WadType#WAD3}.
     * @return Whether or not this lump is compressed
     */
    public boolean isCompressed() {
        return isCompressed;
    }

    /**
     * Compare this lump to another lump. Will only compare the lump name
     * @param o The lump to compare with
     */
    @Override
    public int compareTo(Lump o) {
        if (o == null) {
            return -1;
        }
        return this.getName().compareTo(o.getName());
    }
}
