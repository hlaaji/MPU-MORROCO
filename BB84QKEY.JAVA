/*
BB84 protocol implementation 
 @author Hassan LAAJI- Ph.D researcher at Mahammed First university - Morocco
*/
package bb84qkey;

import java.util.Random;
//import java.util.Iterator;

public class BB84QKEY {
      int n=50;// example we take n=50
      char []Abase=new char[n];// + or x
      char []Apol=new char[n];//H:horizontal; V:vertical ; L:left ; R:right
      int [] Abits=new int[n];//if H-->1; V -->0; L-->0 ; R--> 0
      char []Bbase=new char[n];
      //char []Bpol=new char[n];
      int [] Bbits=new int[n];
      
    /**
     * @param args the command line arguments
     */
 /*******************generation of Alice Base,Polarisy and Serie Bits Abits *************************************/
     char [] ABase(){
    //polarisation  of bit in base
     System.out.println("\n ************Construct of Quantum Key distribution*******\n ");
     System.out.printf("Alice Base Polarised :");
   
    Random rand=new Random();
    for (int i=0;i<n;i++){
    int b = (int)rand.nextInt(2);  //bits key 
    int ba = (int)rand.nextInt(2);
    Abits[i]=b;
        switch (ba){
            case 0 : Abase[i]='+';
                    if (b==0){Apol[i]='V'; }else {Apol[i]='H';}  break;
            
            default : Abase[i]='X';
                       if (b==0){Apol[i]='L'; }else {Apol[i]='R';}  break; 
        }
     System.out.printf(" %C. %C. %1d |",Abase[i],Apol[i],Abits[i]);   
    }
    
    System.out.printf("\n--------------------------------\n");     
             
          
    return Apol; 
  } 
/*******************generation of Bob Base,Polarisy and Serie Bits Bbits *************************************/
  int [] BBase(char [] polA){
    //polarisation  of bit in base
    
     System.out.printf("Generte Bob Basis  :");
    Random rand=new Random();
    for (int i=0;i<n;i++){
    int bb = (int)rand.nextInt(2);
    int pol;
   switch (bb){
       case 0 : Bbase[i]='+';break;
       default  : Bbase[i]='X';break;
       }
    }
   System.out.printf("\n------------------------------------------------\n");
    System.out.printf("Base Bob polarised:");
   for (int i=0;i<n;i++){
        switch (polA[i]){
            case 'V': if (Bbase[i]=='+'){ Bbits[i]=1;} else {Bbits[i]=(int)rand.nextInt(2);} break;
            case 'H': if (Bbase[i]=='+'){ Bbits[i]=0;} else {Bbits[i]=(int)rand.nextInt(2);} break;
            case 'R': if (Bbase[i]=='X'){ Bbits[i]=1;} else {Bbits[i]=(int)rand.nextInt(2);} break;
            case 'L': if (Bbase[i]=='X'){ Bbits[i]=0;} else {Bbits[i]=(int)rand.nextInt(2);} break;
        }
        System.out.printf(" %C. %C. %1d |",Bbase[i],polA[i],Bbits[i]);
    }
   
   System.out.printf("\n------------------------------------------------\n");   
    return Bbits; 
  } 
        
       /********comparison of the basis Alice:Abase and Bob:Bbase***************/
  char [] comparison(){
     System.out.printf("Comparison Bases      :");
      char []comp=new char[n];
      char ba;
      char bb;
      for(int i=0;i<n;i++){
          ba=Abase[i];
          bb=Bbase[i];
          if (ba==bb){comp[i]='Y';} 
            else {comp[i]='N';}
       System.out.printf("  %C  | ",comp[i]);   
      }
  System.out.printf("\n------------------------------------------------\n");   
     return comp; 
  }
  /*********the shared key*******************/
  
  int [] sharedkey(char []comp){
       System.out.printf("***Qubits not modified ***:");
      int []key=new int[n];
      int pos=0;
      for(int i=0;i<n;i++){
          if (comp[i]=='Y'){key[pos]=Bbits[i];
          System.out.printf("posi=%1d .bit:%1d | ",i,key[pos]);pos++;}
      }    
      System.out.printf("\n***Shared key is ***  : ");   
       for(int i=0;i<pos;i++){
           System.out.printf("%1d.",key[i]);
       }      
      double ratio=pos/n;
      System.out.printf("\n ");   
      System.out.printf("\n-----------ratio := %.02f\n",ratio); 
      return key;
  }
  
  // Eve mesurement function (or attacks): it change state of photon polarisation  
   
    char []EVEmesurement(char [] polA) {
        
    //"We suppose that EVE gona mesure  four photons :"
    Random rand=new Random();
    
    System.out.printf("\n***Positions of Qubits meusured (and its polarisation before and after)*** : \n");
    for (int i=0;i<4;i++){
    int bb = (int)rand.nextInt(n);
    int p= (int)rand.nextInt(3);
    System.out.printf("position: %d , before; %c ",bb,polA[bb]);
    switch (polA[bb]){
       case 'H' : switch (p){ 
           case  0 : polA[bb]='V';break;
           case  1 : polA[bb]='L';break;
           case  2 : polA[bb]='R';break;       
                            }
       
       case 'V' : switch (p){ 
           case  0 : polA[bb]='H';break;
           case  1 : polA[bb]='L';break;
           case  2 : polA[bb]='R';break;       
                            }
       case 'L' : switch (p){ 
           case  0 : polA[bb]='V';break;
           case  1 : polA[bb]='H';break;
           case  2 : polA[bb]='R';break;       
                            }
       case 'R' : switch (p){ 
           case  0 : polA[bb]='V';break;
           case  1 : polA[bb]='L';break;
           case  2 : polA[bb]='H';break;       
                            }
       
       }
    System.out.printf("After : %C  \n", polA[bb]);
    }  
      
  
   System.out.printf("\n------------------------------------------------\n");    
   return Apol=polA;
    } 
    
    public static void main(String[] args) {
    // TODO code application logic here
        BB84QKEY obj=new BB84QKEY();
    
        obj.ABase();
       // if we  don't want to include EVE attack we  just take function EVEmeusurement() as comment ;
        obj.EVEmesurement(obj.Apol);
    
        obj.BBase(obj.Apol);
    
        obj.sharedkey(obj.comparison());
    
    }
    
}