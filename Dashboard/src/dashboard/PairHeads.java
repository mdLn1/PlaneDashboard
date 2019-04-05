package dashboard;

// class that stores a pair of values which can be
// considered limits as field names suggest
public final class PairHeads {
    
    int start;
    int end;
    
    @Override
    public String toString()
    {
        return Integer.toString(start) + "-" + Integer.toString(end);
    }
    
    public PairHeads()
    {
        this.start = 0;
        this.end = 100;
    }
    
    public PairHeads(int start, int end)
    {
        this.start = start;
        this.end = end;
    }
    
    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
    
}
