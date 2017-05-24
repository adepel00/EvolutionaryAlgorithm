import java.util.ArrayList;
import java.util.Arrays;
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
    
    
    int n; //TamaÃ±o de la poblacion
    int r; //Longitud de la cadena (numero de genes)
     
    Individuo[] individuos;
    
    int indiceMejorIndividuo;
    
    public AlgoritmoEvolutivo(){
        N_MAX_GENERACIONES = 20;
        PROB_CROSSOVER = 0.8;
        PROB_MUTACION = 0.001;
        CASILLAS_RULETA = 100;
        n = 20;
        r = 10;
        
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
            System.out.println("---------------------------------------------------------------------");
            System.out.println("Mejor individuo entrante " + individuos[indiceMejorIndividuo]);
            System.out.println("Individuos entrantes " + Arrays.toString(individuos));
    //------------------------Seleccion--------------------------------
            //Asignar casillas de la ruleta
            asignarCasillas();
            
            //Tirar la ruleta
            tirarLaRuleta(); 
            
            System.out.println("Individuos postseleccion " + Arrays.toString(individuos));
    //-------------------------Crossover-------------------------------
	        ArrayList<Individuo> individuosCruzanComprobar = getIndividuosCruzan();
	        if(individuosCruzanComprobar.size() % 2 != 0){ //Tenemos un número impar de individuos, tenemos que quitar uno
	        	int indiceQuitar = randomInt(individuosCruzanComprobar.size() - 1, 0);
	        	for(int j = 0; j < n; j++){
	            	if(individuos[j] == null){
	            		individuos[j] = individuosCruzanComprobar.get(indiceQuitar);
	            		break;
	            	}
	            }
	        	individuosCruzanComprobar.remove(indiceQuitar);
	        }
	        Individuo[] individuosCruzan = new Individuo[individuosCruzanComprobar.size()];
	        
	        Individuo[] individuosCruzados = cruzar(individuosCruzanComprobar.toArray(individuosCruzan));
	        int count = 0; //Contador de cruzados añadidos al array de individuos
	        for(int j = 0; j < n; j++){
	        	if(individuos[j] == null && individuosCruzados.length > count){
	        		individuos[j] = individuosCruzados[count];
	        		count++;
	        	}
	        }
	        
	        System.out.println("Individuos postcrossover " + Arrays.toString(individuos));
	     //-------------------------Mutación-------------------------------
	        for(int k = 0; k < n; k++){
	        	for(int t = 0; t < r; t++){
	        		mutar(k, t);
	        	}
	        }
	        System.out.println("Individuos postmutacion " + Arrays.toString(individuos));
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
      			   break;
      		   }
      	   }
         }
    	individuos = individuosSeleccionados;
    }
    
    private ArrayList<Individuo> getIndividuosCruzan(){
	   ArrayList<Individuo> individuosCruzan = new ArrayList<>();
	   for(int i = 0; i < n; i++){
		   double aleatorio = randomDouble(0, 1);
		   if(aleatorio <= PROB_CROSSOVER){
			   individuosCruzan.add(individuos[i]);
			   individuos[i] = null;
		   }		   
	   }
	   return individuosCruzan;
    }
    
    private Individuo[] cruzar(Individuo[] individuosCruzan){
    	int corte = randomInt(individuosCruzan[1].getGenes().length - 1, 0);
    	for(int i = 0; i < individuosCruzan.length; i++){
    		for(int j = 0; j <= corte; j++){
    			//Intercambio de genes
        		int aux = individuosCruzan[i].getGenes()[j];
        		individuosCruzan[i].getGenes()[j] = individuosCruzan[i + 1].getGenes()[j];
        		individuosCruzan[i + 1].getGenes()[j] = aux;
        	}
    		i++;
    	}
    	return individuosCruzan;
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
    
    private void mutar(int k, int t) {
    	double aleatorio = randomDouble(1, 0);
		if(aleatorio <= PROB_MUTACION){
			if(individuos[k].getGenes()[t] == 0){
				individuos[k].getGenes()[t] = 1;
			}else{
				individuos[k].getGenes()[t] = 0;
			}
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
