

import java.math.BigInteger;
import java.util.Random;


public class RSA_Algorithm {

	public static void main(String[] args) {
	BigInteger e,RandomNumber_p,RandomNumber_q,RandomNumber_a;
               BigInteger     RandomNumber_b, Arg_d, Arg_n, Arg_e, Arg_p ,Arg_q ,fn ,Encrypted_msg;
               boolean prime_p,prime_q;
	try{	
	
            if(args.length==0||args.length==5)
		{
			System.out.println("Wrong output Format");
			System.exit(1);
			
		}
		
		if(args.length==1)
		{
					
			//call to random prime generator with input size ...		
			System.out.println("The size of prime number is :"+args[0]);
			int lengthp=Integer.parseInt(args[0]);
			do
			{
				//Generate Random number
				RandomNumber_p=randomBigInteger(lengthp);
				// Test if the generated number is prime
				prime_p=isPrime(RandomNumber_p);
								
				}while(prime_p==false);
			do
			{
				RandomNumber_q=randomBigInteger(lengthp);
				prime_q=isPrime(RandomNumber_q);
			}   while(prime_q==false);
			
			//calculation of fi_of n
			fn= fi_of_n(RandomNumber_p,RandomNumber_q);
			//calculation of e,which is coprime to fi_of n
			e=calculate_E(fn);
			System.out.println("The prime number p:"+RandomNumber_p);
			System.out.println("The prime number q:"+RandomNumber_q);
			System.out.println("The value of fn:"+fn);
			System.out.println("The value of e:"+e);
}
		if (args.length==2) {
			
			  String a = args[0];
		      String b = args[1];
		      BigInteger[] bArray=new BigInteger[3];
		      RandomNumber_a=new BigInteger(a);
		      RandomNumber_b=new BigInteger(b);
		      System.out.println("a="+a+", b="+b);
		    //call Extended Euclidean Algorithm ...
		      bArray=ExtendedEu(RandomNumber_a,RandomNumber_b);
		      System.out.println("The value of GCD: "+bArray[0]);
		      System.out.println("The value of X: "+bArray[1]);
		      System.out.println("The value of Y: "+bArray[2]);
		   }
		   if (args.length==3) {
			  String p = args[1];
		      String q = args[2];
		      String e3 = args[0];
		      Arg_e=new BigInteger(e3);
		      Arg_p=new BigInteger(p);
		      Arg_q=new BigInteger(q);
		      System.out.println("p="+p+", q="+q+" ,e"+e3);
		      BigInteger[] enArray=new BigInteger[4];
		      //call your mod inverse func ...and calculate d by passing fi_of n and e
		      enArray= ExtendedEu_pqe(Arg_p,Arg_q,Arg_e);
		        System.out.println("GCD: "+enArray[0]);
				System.out.println("X: "+enArray[1]);
				System.out.println("Y: "+enArray[2]);	
				System.out.println("The value of  n:"+enArray[3]);
				System.out.println("The value of  D:"+enArray[1]);
				//boolean b1=verifyD(d,fn,e);
				//System.out.print(b1);
	   }
		   if (args.length==4) {
			      if (args[0].equalsIgnoreCase("e")||args[0].replaceAll("\'","").equals("e")) {	  		    	 
		         String e4 = args[1];
		         String n4 = args[2];
		         String m = args[3];
		         Arg_e=new BigInteger(e4);
		         Arg_n=new BigInteger(n4);
		        
		         System.out.println("e="+e4+", n="+n4+" ,m="+m);
		         //call  RSA encryption ...
		         Encrypted_msg=RSAEncryption(Arg_e,Arg_n,m);
		         System.out.print("Encrypted Message is "+Encrypted_msg);
		                  
		      }
		      else if (args[0].equalsIgnoreCase("d")||args[0].replaceAll("\'", "").equals("d")){
		    	  String d5 = args[1];
		          String n5 = args[2];
		          String m = args[3];
		          System.out.println("d="+d5+", n="+n5+" ,m"+m);
		          Arg_d=new BigInteger(d5);
		          Arg_n=new BigInteger(n5);
			      Encrypted_msg=new BigInteger(m);
			      String Message_Decypt;
			      //call  RSA decryption ...
			      Message_Decypt=RSADecryption(Arg_d,Arg_n,Encrypted_msg);	
			      System.out.println("Message is -->"+Message_Decypt);
			     		      
		       }
		    }
	
		    
		// TODO Auto-generated method stub
}catch(Exception exp)
{
System.out.println("Exception occured "+exp.getMessage());
}
	}
	 //Calculation of random number with size n
	public static BigInteger randomBigInteger(int n) {
		//Get the Max and Min value for the number of size n
	    BigInteger	MinNumber=getLowerBound(n);
	    BigInteger  MaxNumber=getUpperBound(n);
	    Random rnd = new Random();
	    int maxNumBitLength = MaxNumber.bitLength();
	    BigInteger aRandomBigInt;
	   
	        do {
	        	 aRandomBigInt = new BigInteger(maxNumBitLength, rnd);
	          
	           
	        } while (aRandomBigInt.compareTo(MaxNumber) ==1  || aRandomBigInt.compareTo(MinNumber)==-1);
	        //System.out.println(aRandomBigInt);
	        return aRandomBigInt;
	    }
	// Maximum value of a number with size len
	 public static BigInteger getUpperBound(int len) {
			
	    	BigInteger b= BigInteger.TEN;
			b = b.pow(len);
			b = b.subtract(BigInteger.ONE);
			//System.out.println(b);
			return b;
		}
	// Minimum value of a number with size len
		public static BigInteger getLowerBound(int len) {
			
			BigInteger a= BigInteger.TEN;
			a = a.pow(len-1);
			//System.out.println(a);
			return a;
		}
		//Fermat's test for prime
		 public static boolean isPrime(BigInteger random)
		    {
		    	BigInteger b=new BigInteger("2");
		    	BigInteger two1=new BigInteger("2");
		    	//if number is even its not prime so eliminate it
		    	if (random.mod(two1) == BigInteger.ZERO && random.compareTo(two1)==1)
				 {
					 //System.out.println("first elimination");
					 return false;				 
				 }
		    	else{
		    		//For b value 2 
		    	if(mod_exp(b,random.subtract(BigInteger.ONE),random).compareTo(BigInteger.ONE)==0)
		    	{
		    		//For b value 3
		    		b=b.add(BigInteger.ONE);
		    		if(mod_exp(b,random.subtract(BigInteger.ONE),random).compareTo(BigInteger.ONE)==0)
		    		return true;
		    		else 
		    			return false;
		    	}
		    	
		    	else
		    	{
		    		return false;
		        }
		    	}
		    }
		 //Implementation of repeated(recursive) squaring algorithm
		 public static BigInteger mod_exp(BigInteger x,BigInteger y,BigInteger m)
			{
				BigInteger z;
				BigInteger two1=new BigInteger("2");
				
				//System.out.println("value of y"+y);
											
			if (y.compareTo(BigInteger.ZERO) == 0)
			{
				//System.out.println("y is zero");
				
				 return BigInteger.ONE;
			}
			else
			{
				z = mod_exp(x, y.divide(two1), m);
				//System.out.println("value of z"+z);
				if( y.mod(two1) == BigInteger.ZERO) 
				{
					//System.out.println(("Z value is"+z.multiply(z.mod(m)).mod(m)));
					return(z.multiply(z.mod(m)).mod(m));
				}
				else return(x.multiply(z.multiply(z.mod(m)).mod(m)).mod(m));
			}
				 }
		
		 //calculation fi_of n
		 public static BigInteger fi_of_n(BigInteger p,BigInteger q)
		 {
			 BigInteger fn;
			 //fn=p-1*q-1
			 p=p.subtract(BigInteger.ONE);
			 q=q.subtract(BigInteger.ONE);
			 fn=p.multiply(q);
			 return fn;
		 }
			//calculation of e
		 public static BigInteger calculate_E(BigInteger fn)
		 {
			
			 BigInteger e=new BigInteger("10");
			 BigInteger result;
            // e and fn are coprime so GCD =1
			do{
				if(e.compareTo(fn)==-1)
				{
				e=e.add(new BigInteger("1"));
			
				 result=gcd_EU(e,fn);
				}else
				{
					System.out.println("There is no value for e ");
					
					return BigInteger.ZERO;
				}
			}while(result.compareTo(BigInteger.ONE)==1||result.compareTo(BigInteger.ONE)==-1);
			 return e;
		 }
		 //GCD Euclid's Algorithm
		 public static BigInteger gcd_EU(BigInteger a,BigInteger b)
		 {
			 if(a.compareTo(b)==1)
			 {
			 if( b.compareTo(BigInteger.ZERO) == 0)
				 return a;
			 else
			 return gcd_EU(b,a.mod(b));
			 }
			 else 
				return gcd_EU(b,a);
		 }
		//Extended Euclidean Algorithm for a,b
		 public static BigInteger[] ExtendedEu(BigInteger a,BigInteger b)
		 {
			 BigInteger[] bArray=new BigInteger[3];
			 BigInteger[] aArray=new BigInteger[3];
			 BigInteger d1,x1,y1,d,x,y;
		     
				if (b.compareTo(BigInteger.ZERO)==0)
				{
					bArray[0]=a;
					bArray[1]=BigInteger.ONE;
					bArray[2]=BigInteger.ZERO;
					return bArray;
				}
				bArray=ExtendedEu(b, a.mod(b));
				d1=bArray[0];
				x1=bArray[1];
				y1=bArray[2];
				d=d1;
				x=y1;
				y=x1.subtract((a.divide(b)).multiply(y1));
				//(d,x,y)=(d1, y1, (x1-(a.divide(b)) y1));
				aArray[0]=d;
				aArray[1]=x;
				aArray[2]=y;
				return aArray;

			 
		 }
		 //Extended Euclidean Return d and n,for mod inverse
		 public static BigInteger[] ExtendedEu_pqe(BigInteger a,BigInteger b,BigInteger e)
		 {
			
			 BigInteger[] bArray=new BigInteger[4];
			 BigInteger[] aArray=new BigInteger[4];
			 BigInteger d1,x1,y1,d,x,y;
			 aArray[3]=a.multiply(b);
			 bArray[3]=a.multiply(b);
			 BigInteger fn=fi_of_n(a,b);
			 //passing e and fn as inputs to get value d(multiplicative inverse of e)
		     a=e;
		     b=fn;
				if (b.compareTo(BigInteger.ZERO)==0)
				{
					bArray[0]=a;
					bArray[1]=BigInteger.ONE;
					bArray[2]=BigInteger.ZERO;
					return bArray;
				}
				bArray=ExtendedEu(b,a.mod(b));
				d1=bArray[0];
				x1=bArray[1];
				y1=bArray[2];
				d=d1;
				x=y1;
				y=x1.subtract((a.divide(b)).multiply(y1));
				//(d,x,y)=(d1, y1, (x1-(a.divide(b)) y1));
				aArray[0]=d;
				aArray[1]=x;
				aArray[2]=y;
				//to make d positive
				if(x.compareTo(BigInteger.ZERO)==-1)
				{
					x=x.add(fn);
		            System.out.println("X is negative so add fn, after adding value of x:"+x);
		            aArray[1]=x;
				}
				//System.out.println(aArray[3]);
				return aArray;
	}
		/* public static boolean verifyD(BigInteger d,BigInteger fn,BigInteger e)
			{
				if((d.multiply(e)).mod(fn).compareTo(BigInteger.ONE)==0)
				{
				return true;
				}
				else return false;
			}*/
         //RSA Encryption 
		 public static BigInteger RSAEncryption(BigInteger e,BigInteger n,String msg){
			 //System.out.println(msg.length());
			 //Converting message string to ASCII
			 BigInteger total=new BigInteger("0");
			 int Ascii=0;
			 int sum=0;
			 BigInteger sumBig=new BigInteger("1");
			 for(int i=msg.length()-1;i>=0;i--)
			 {
			 Ascii=(int)msg.charAt(i);
			 //System.out.println(" Message character :"+msg.charAt(i)+" Ascii value :"+Ascii);
			 sum=Ascii;
			 sumBig=BigInteger.valueOf(sum);
			 for(int j=msg.length()-1;j>i;j--)
			 {
				
				sumBig=(sumBig.multiply(new BigInteger("128"))); 
			}
			 total=total.add(sumBig);
			
			 }
			// System.out.println("Total"+total);
			 
			 BigInteger PM=mod_exp(total,e,n);
			 return PM;
		 }
		 //RSA Decryption
		 public static String RSADecryption(BigInteger d,BigInteger n,BigInteger toDecrypt)
		 {
			 String output =" ";
			 BigInteger decrypt=mod_exp(toDecrypt,d,n);
			 output= Get_msg(decrypt);
			 return output;
		 }
		 //Convert from ASCII to String
		 public static String Get_msg(BigInteger decrypt)
		 {  
	         BigInteger value=decrypt;
			 String s=" ";
			 BigInteger intermediate=new BigInteger("0");
			 StringBuffer a = new StringBuffer();
		     for(int i=0;value.compareTo(BigInteger.ZERO)==1;i++)
		    {
		     intermediate=value;
		     intermediate=intermediate.mod(new BigInteger("128"));
		     value =(value.divide(new BigInteger("128")));
		     int k=(int)intermediate.intValue();
			 char ch = (char) k;
			 a.append(ch);
		    }
		   			return a.reverse().toString();
		 }
		
		
}

