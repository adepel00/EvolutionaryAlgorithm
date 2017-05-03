package algoritmoevolutivo;

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
    
    private void initialization(){
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
    
    private void algoritmo(){
       for(int i = 0; i < N_MAX_GENERACIONES; i++){
           //Calculo mejor individuo
           indiceMejorIndividuo = 0;
           for(int j = 0; j < n; j++){
               if(individuos[j].aptitud() > individuos[indiceMejorIndividuo].aptitud()){
                   indiceMejorIndividuo = j;
               }else if(individuos[j].aptitud() == individuos[indiceMejorIndividuo].aptitud()){
                   //Si las aptitudes son iguales se coge aleatoriamente uno de los dos
                   int random = randomInt(0, 1);
                   if(random == 1){
                       indiceMejorIndividuo = j;
                   }
               }
                       
           }
           //Seleccion
           //TODO si es 0 la probabilidad ponerle una casilla
           //Calcular casillas de la ruleta
           int ultimaCasillaAsignada = 0;
           for(int j = 0; j < n; j++){
               individuos[j].setCasillaFinal(ultimaCasillaAsignada + (int) (CASILLAS_RULETA * calculaProbabilidad(individuos[j])) - 1);
               ultimaCasillaAsignada = individuos[j].getCasillaFinal();
           }
           if(ultimaCasillaAsignada + 1 < CASILLAS_RULETA){
               
           }
       } 
    }
    
    private double calculaProbabilidad(Individuo individuo){
        int sumatorioAptitudes = 0;
        for(int i = 0; i < n; i++){
            sumatorioAptitudes += individuos[i].aptitud();
        }
        return individuo.aptitud()/sumatorioAptitudes;
    }
    
    private int randomInt(int m, int n){
        return (int) Math.floor(Math.random()*(n-m+1)+m);
    }
    
    public static void main() {
        AlgoritmoEvolutivo algoritmoEvolutivo = new AlgoritmoEvolutivo();
        algoritmoEvolutivo.initialization();
        algoritmoEvolutivo.algoritmo();
        
    }
}
