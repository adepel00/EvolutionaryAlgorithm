/**
 *
 * @author Alba de Pedro
 */
public class Individuo {
    int[] genes;
    int casillaFinal;
    
    public Individuo(int r){
        this.genes = new int[r];
        for(int i = 0; i < r; i++){
            genes[i] = (int) Math.floor(Math.random()*2);
        }
    }
    
    public int[] getGenes(){
        return genes;
    }
    
    public int aptitud(){
        int unos = 0;
        for(int i = 0; i < genes.length; i++){
            unos += genes[i];
        }
        return unos;
    }
    
    public void setCasillaFinal(int casilla){
        casillaFinal = casilla;
    }
    
    public int getCasillaFinal(){
        return casillaFinal;
    }
    
    @Override
    public String toString(){
    	StringBuilder individuo = new StringBuilder();
    	for(int i = 0; i < genes.length; i++){
    		individuo.append(genes[i]);
    	}
    	return individuo.toString();
    }
}
