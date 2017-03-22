import java.util.Scanner;
import java.util.Random;
import jsjf.CircularArrayQueue;

public class Main {
	//Variabler
	public static int method, n;
	public static boolean perform, estimate, print = false;
	public static int arr[];
	
	//Konstanter brukt under estimering. 
	//Verdiene er generert via gjennomsnittet (100 tester) av tidenen til sorteringen.
	public static final double C_QUICK = 0.00002;
	public static final double C_MERGE = 0.000032;
	public static final double C_INSRT = 0.000000177;
	public static final double C_RADIX = 0.0002;	

	public static void main(String[] args) {
		//Scan input
		scanInput();
		
		//Opprett Array
		if(estimate)
			estimateSort();		
		
		if(perform)	{
			makeArray();
			performSort();
		}
		
	}
	
	private static void makeArray(){
		//Oppretting av et array med tilfeldige genererte verdier som kan være dobbelt så stor som størrelsen på arrayet
		Random r = new Random();
		arr = new int[n];
		
		for(int i =0; i<n; i++){
			arr[i] = r.nextInt(n*2);
		}	
		
		if(print)
			printArray();
	}
	
	private static void printArray(){
		
		
		for(int i =0; i<n; i++){
			if(i%30==0) 
				System.out.println("");
			
			System.out.print(arr[i] + ", ");
			
		}
		System.out.println(" ");
	}
	
	private static void estimateSort(){
		//Utregner estimert tid til den valgte sorteringsmetoden
		System.out.print("Estimeringen av sorteringen er: ");
		
		switch(method){
			case 1: System.out.print((int)Math.floor(C_INSRT*(n^2))); break;
		
			case 2: System.out.print((int)Math.floor(C_QUICK*n*Math.log10(n))); break;
			
			case 3: System.out.print((int)Math.floor(C_MERGE*n*Math.log10(n))); break;
			
			case 4: System.out.print((int)Math.floor(C_RADIX*n)); break;
			
			default: System.out.println("Hvordan kom du hit? plz tell");
		}
		
		System.out.println(" millisekunder");
		
	}
	
	private static void performSort(){
		//Starter sorteringenmetoden som er valgt
		System.out.println("Starter sorteringen av array ...");
		long startSort = System.currentTimeMillis();
		
		switch(method){
			case 1: insertionSort(arr); break;
		
			case 2: quickSort(arr, 0, n-1); break;
			
			case 3: mergeSort(arr, 0, n-1); break;
			
			case 4: radixSort(arr, (int)Math.log10(n*2)+1); break;
			
			default: System.out.println("Hvordan kom du hit? plz tell");
		}
		
		long stopSort = System.currentTimeMillis();
		
		if(print)
			printArray();
		
		System.out.println("Sorteringen tok: " + (stopSort - startSort) + " millisekunder.");
	}
	
	private static void scanInput(){
		boolean inputerror = true;
		//Venter på input fra bruker, for å få inn noen variabler.
		Scanner s = new Scanner(System.in);
		
		//do while og try catch for sjekk av input
		
		//Metode for sortering
		do{
			System.out.print("Hvilken methode skal brukes? (Velg tallnummer)");
			System.out.print("\n1: Innstikk\n2: Quick\n3: Merge\n4: Radix\n");
			try{ 
				method = s.nextInt();
				inputerror = false;
			} 
			catch(Exception e){
				System.out.println("Feil!");
				s.next();
			}
		} while (inputerror || method > 4 || method < 1);
		
		inputerror = true;
		
		//Størrelse på array
		do{
			System.out.print("Velg antall tall som skal sorteres (min 2): \n");
			try{ 
				n = s.nextInt(); 
				inputerror = false;
			}
			catch(Exception e){
				System.out.println("Feil!");
				s.next();
			}
		} while (inputerror || n < 2);
		
		inputerror = true;
		
		//Utføring/Estimering
		int tempInt = 0;
		do{
			System.out.print("Hva skal gjøres?: ");
			System.out.print("\n1: Utføre sortering\n2: Estimere sortering\n3: Begge\n");
			try{ 
				tempInt = s.nextInt();
				inputerror = false;
			}
			catch(Exception e){
				System.out.println("Feil!");
				s.next();
			}
		} while (inputerror || tempInt >3 || tempInt <1);
		
		if(tempInt == 1){ perform = true; }
		else if (tempInt == 2){ estimate = true; }
		else { perform = true; estimate = true; }
		
		
		
		//Utprinting
		if(perform){
			
			inputerror = true;
			String tempString = "";
			
			do{
				System.out.println("Skal arrayet printes ut? Dette tar lang tid for store verdier av n. (y/n): ");
				try{ 
					tempString = s.next();
					inputerror = false;
				}
				catch(Exception e){
					System.out.println("Feil!");
					s.next();
				}
			} while (inputerror || (!tempString.equals("y") && !tempString.equals("n") && !tempString.equals("Y") && !tempString.equals("N")));
			
			if(tempString.equals("y") || tempString.equals("Y"))
				print = true;
		}
		//Stopper inputscanneren
		s.close();
	}
	
	//Innstikk - Hentet fra forelesningsnotatene
	public static void insertionSort(int A[])
    {
		// Innstikksortering av array med heltall
	
		int n = A.length;
		int key;
	
		for (int i = 1; i < n; i++)
		{
		    // A er sortert t.o.m. indeks i-1
		    key = A[i];
		    int j = i;
		    // Setter element nummer i på riktig plass
		    // blant de i-1 første elementene
		    while (j > 0 && A[j-1] > key)
		    {
			A[j] = A[j-1];
			j--;
		    }
		    A[j] = key;
		}
    }
		
	//Quick - Hentet fra forelesningsnotatene
	public static void quickSort(int A[], int min, int max)
    {
		// Quicksort av array med heltall
	
		int indexofpartition;
	
		if (max - min  > 0)
		{
		    // Partisjonerer array
		    indexofpartition = findPartition(A, min, max);
	
		    // Sorterer venstre del
		    quickSort(A, min, indexofpartition - 1);
	
		    // Sorterer høyre del
		    quickSort(A, indexofpartition + 1, max);
		}
    }
	//Henger med quicksort
	public static int findPartition (int[] A, int min, int max)
	{
		int left, right;
		int temp, partitionelement;

		// Bruker *første* element til å dele opp
		partitionelement = A[min]; 

		left = min;
		right = max;
	   
		// Gjør selve partisjoneringen
		while (left < right)
		{
		    // Finn et element som er større enn part.elementet
		    while (A[left] <= partitionelement && left < right)
			left++;

		    // Finn et element som er mindre enn part.elementet
		    while (A[right] > partitionelement)
			right--;

		    // Bytt om de to hvis ikke ferdig
		    if (left < right)
		    {
			temp = A[left];
			A[left] = A[right];
			A[right] = temp;
		    }
		}

		// Sett part.elementet mellom partisjoneringene
		temp = A[min];
		A[min] = A[right];
		A[right] = temp;
		
		// Returner indeksen til part.elementet
		return right;
	}
	
	//Merge - Hentet fra forelesningsnotatene
	public static void mergeSort (int[] A, int min, int max)
    {
	
		// Flettesortering av array med heltall
	
		if (min==max)
		    return; 
	
		int[] temp;
		int index1, left, right;
		int size = max - min + 1;
		int mid = (min + max) / 2;
	
		temp = new int[size];
	      
		// Flettesorterer de to halvdelene av arrayen
		mergeSort(A, min, mid); 
		mergeSort(A, mid + 1,max);
	
		// Kopierer array over i temp.array
		for (index1 = 0; index1 < size; index1++)
		    temp[index1] = A[min + index1];
	      
		// Fletter sammen de to sorterte halvdelene over i A
		left = 0;
		right = mid - min + 1;
		for (index1 = 0; index1 < size; index1++)
		{
		    if (right <= max - min)
			if (left <= mid - min)
			    if (temp[left] > temp[right])
				A[index1 + min] = temp[right++];
			    else
				A[index1 + min] = temp[left++];
			else
			    A[index1 + min] = temp[right++];
		    else
			A[index1 + min] = temp[left++];
		}
    }
	
	//Radix - Hentet fra forelesningsnotatene
	public static void radixSort(int a[], int maksAntSiffer)
    {
		// Radixsortering av en array a med desimale heltall
		// maksAntSiffer: Maksimalt antall siffer i tallene
	
		int ti_i_m = 1; // Lagrer 10^m 
		int n = a.length;
	
		// Oppretter 10 tomme køer 
		CircularArrayQueue<Integer>[] Q = 
		    (CircularArrayQueue<Integer>[])(new CircularArrayQueue[10]);
	
		for (int i = 0; i < 10; i++)
		    Q[i] = new CircularArrayQueue<Integer>(); 
	            
		// Sorterer på et og et siffer, fra venstre mot høyre
	
		for (int m = 0; m < maksAntSiffer; m++)
		{
		    // Fordeler tallene i 10 køer
		    for (int i = 0; i < n; i++)
		    {
			int siffer = (a[i] / ti_i_m) % 10;
			Q[siffer].enqueue(new Integer(a[i]));
		    }
	
		    // Tømmer køene og legger tallene fortløpende tilbake i a
		    int j = 0;
		    for (int i = 0; i < 10; i++)
			while (!Q[i].isEmpty())
			    a[j++] = (int) Q[i].dequeue();
	
		    // Beregner 10^m for neste iterasjon
		    ti_i_m *= 10;
		}
    }

}
