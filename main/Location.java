package main;
public class Location{
    int fileno;
    int pos;
    int artNo;

    public Location(int Fileno, int ArtNo, int Pos) {
        fileno = Fileno;
        artNo = ArtNo;
        pos = Pos;
    }
    public int getFileno() {
        return fileno;
    }

    public int getPos() {
        return pos;
    }

    public void setFileno(int fileno) {
        this.fileno = fileno;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setArtNo(int artNo) {
        this.artNo = artNo;
    }

    public int getArtNo() {
        return artNo;
    }


}
/////////////////////////////




















