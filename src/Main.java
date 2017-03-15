import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.lang.*;

import jsjf.CircularArrayQueue;

public class Main {
	//Variabler
	public static int method, n, tempInt;
	public static boolean perform, estimate;
	public static int arr[];
	

	public static void main(String[] args) {
		//Scan input
		scanInput();
		//Opprett Array
		makeArray();
		
		printuArrayuDesu();
		
		performSort();
		
		printuArrayuDesu();
	}
	
	private static void makeArray(){
		Random r = new Random();
		arr = new int[n];
		
		for(int i =0; i<n; i++){
			arr[i] = r.nextInt(n*2);
		}		
	}
	
	private static void printuArrayuDesu(){
		for(int i =0; i<n; i++){
			System.out.print(arr[i] + ", ");
		}
		System.out.println(" ");
	}
	
	private static void performSort(){
		switch(method){
			case 1: insertionSort(arr); break;
		
			case 2: quickSort(arr, 0, n-1); break;
			
			case 3: mergeSort(arr, 0, n-1); break;
			
			case 4: radixSort(arr, (int)Math.log10(n*2)+1); break;
			
			default: System.out.println("Hvordan kom du hit? plz tell");
		}
	}
	
	private static void scanInput(){
		boolean inputerror = true;
		//Venter på input fra bruker, for å få inn noen variabler.
		Scanner s = new Scanner(System.in);
		
		//do while og try catch for sjekk av input
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
		
	//Quick
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
	
	//Merge
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
	
	//Radix
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
