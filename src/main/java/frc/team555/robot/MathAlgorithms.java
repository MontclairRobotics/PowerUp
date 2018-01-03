package frc.team555.robot;

public class MathAlgorithms {

    public double avg(double ... values){
        double total = 0;
        for(double value : values){
            total += value;
        }
        return total / values.length;
    }

    public double stdDiv(double ... values){
        double avg = avg(values);
        double total = 0;
        for(double value : values){
            total += Math.pow(value - avg, 2);
        }
        return Math.sqrt(total / values.length);

    }

    public double checkDiffDT(double current, double avg){return Math.abs(current - avg);}

    public boolean checkSTDDT(double current, double avg, double stdDiv){ return checkDiffDT(current,avg) > stdDiv; }

}
