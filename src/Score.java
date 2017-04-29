/**
 * Created by Hacio-PC on 2016-04-16.
 */
public class Score {
    private String name;
    private int wins;
    private int draws;
    private int losses;

    public Score(String n, int w, int d, int l)
    {
        setName(n);
        setWins(w);
        setDraws(d);
        setLosses(l);
    }
    public Object getValueOfIndex(int index)
    {
        if(index==0) return name;
        if(index==1) return wins;
        if(index==2) return draws;
        if(index==3) return losses;
        else
        return 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }
}


