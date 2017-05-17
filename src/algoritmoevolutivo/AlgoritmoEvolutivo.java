import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Alba de Pedro
 */
public class AlgoritmoEvolutivo {
    
    int N_MAX_GENERACIONES;
    double PROB_CROSSOVER;
    double PROB_MUTACION;
    int CASILLAS_RULETA;
    
    
    int n; //Tamaño de la poblacion
    int r; //Longitud de la cadena (numero de genes)
     
    Individuo[] individuos;
    
    int indiceMejorIndividuo;
    
    public AlgoritmoEvolutivo(){
        N_MAX_GENERACIONES = 2;
        PROB_CROSSOVER = 0.8;
        PROB_MUTACION = 0.001;
        CASILLAS_RULETA = 100;
        n = 5;
        r = 5;
        
        individuos = new Individuo[n];
        for(int i = 0; i < n; i++){
            individuos[i] = new Individuo(r);
        }
    }
    
    public static void main(String[] args){
        AlgoritmoEvolutivo algoritmoEvolutivo = new AlgoritmoEvolutivo();
        algoritmoEvolutivo.algoritmo();
        
    }
    
    private void algoritmo(){
        for(int i = 0; i < N_MAX_GENERACIONES; i++){
            //Calculo mejor individuo
            indiceMejorIndividuo = getIndiceMejorIndividuo();
            
    //------------------------Seleccion--------------------------------
            //Asignar casillas de la ruleta
            asignarCasillas();
            
            //Tirar la ruleta
            tirarLaRuleta();
        } 
    //-------------------------Crossover-------------------------------
        Individuo[] individuosCruzan = getIndividuosCruzan();
        cruzar(individuosCruzan);
        for(int j = 0; j < n; n++){
        	
        }
        
     }
    
    private int getIndiceMejorIndividuo(){
    	int indice = 0;
        for(int j = 0; j < n; j++){
            if(individuos[j].aptitud() > individuos[indice].aptitud()){
            	indice = j;
            }else if(individuos[j].aptitud() == individuos[indice].aptitud()){
                //Si las aptitudes son iguales se coge aleatoriamente uno de los dos
                int random = randomInt(0, 1);
                if(random == 1){
                	indice = j;
                }
            }
                    
        }
        return indice;
    }
    
    private void asignarCasillas(){
    	int ultimaCasillaAsignada = 0;
        for(int j = 0; j < n; j++){
     	   int numeroCasillas = (int) (CASILLAS_RULETA * calculaProbabilidad(individuos[j]));       	   
            individuos[j].setCasillaFinal(ultimaCasillaAsignada + numeroCasillas - 1);
            ultimaCasillaAsignada = individuos[j].getCasillaFinal();
        }
        if(ultimaCasillaAsignada + 1 < CASILLAS_RULETA){
            int casillasSinAsignar = CASILLAS_RULETA - (ultimaCasillaAsignada + 1);
            for(int k = indiceMejorIndividuo; k < n; k++){
         	   individuos[k].setCasillaFinal(individuos[k].getCasillaFinal() + casillasSinAsignar);
            }
        }
    }
    
    private void tirarLaRuleta(){
    	Individuo[] individuosSeleccionados = new Individuo[n];
    	for(int x = 0; x < n; x++){
      	   int casillaAleatoria = randomInt(0, CASILLAS_RULETA - 1);
      	   for(int y = 0; y < n; y++){
      		   if(individuos[y].getCasillaFinal() >= casillaAleatoria){
      			   individuosSeleccionados[x] = individuos[y];
      		   }
      	   }
         }
    	individuos = individuosSeleccionados;
    }
    
    private Individuo[] getIndividuosCruzan(){
	   ArrayList<Individuo> individuosCruzan = new ArrayList<>();
	   for(int i = 0; i < n; i++){
		   double aleatorio = randomDouble(0, 1);
	   }
	   return individuosCruzan.toArray(new Individuo[individuosCruzan.size()]);
    }
    
    private Individuo[] cruzar(Individuo[] individuosCruzan){
    	
    }
    
    private double calculaProbabilidad(Individuo individuo){
        int sumatorioAptitudes = 0;
        for(int i = 0; i < n; i++){
            sumatorioAptitudes += individuos[i].aptitud();
        }
        if(sumatorioAptitudes == 0){
        	return 0;
        }else{
        	return individuo.aptitud()/(double) sumatorioAptitudes;
        }
    }
    
    private int randomInt(int m, int n){
        return (int) Math.floor(Math.random()*(n-m+1)+m);
    }
    
    private double randomDouble(double m, double n){
    	Random r = new Random();
    	return n + (m - n) * r.nextDouble();
    }
}
