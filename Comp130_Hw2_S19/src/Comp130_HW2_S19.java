
/* Student Name:Ekrem Yiðiter
 * This program implements a simple RSA algorithm for the
 * encryption and decryption of a message.
 * A 5-digit integer message is entered by the user 
 * and RSA algorithm encrypt every digit in the message
 * decryptes back the message again and display on the console.
 */
import acm.program.ConsoleProgram;
import acm.util.RandomGenerator;

public class Comp130_HW2_S19 extends ConsoleProgram {

	public void run() {
		//your code starts here
		produceEncrptionKey();
		produceDecrptionKey();
		println("Producing Encrption and its required variables");
		println("		The value pPrime is " + pPrime + " and qPrime is " + qPrime);
		println("		The totient value of pPrime and qPrime is " + totient);
		println("		The n value of pPrime and qPrime is " + nValue);
		println("		The encryption key is " + encryptionKey);
		println("		The decryption key is " + decryptionKey);
		getUserMessage();
		println("The message that is being encrypted is " + message);
		encrypteDigits();
		encryptDescrMessage();
		
		
		
		// your code ends here
	}
	/**
	 * This method produced the encrption key and assigns the
	 * variables required to create the encrption key as
	 * instance variables of this class.
	 */

	private void produceEncrptionKey() {
		//your code starts here
		pPrime = rgen.nextInt(LOWER_BOUND,UPPER_BOUND);
		boolean isPrimeP = false;
		while (isPrimeP = false) {
			for (int i=2; i<pPrime; i++) {
				int k=i;
				if (pPrime%k == 0) {
					isPrimeP = true;
				}
			}
			if (isPrimeP==true) {
				pPrime = rgen.nextInt(LOWER_BOUND,UPPER_BOUND);
				isPrimeP=false;
			}else {
				isPrimeP=true;
			}
		}
		qPrime = rgen.nextInt(LOWER_BOUND,UPPER_BOUND);
		while (pPrime == qPrime) {
			qPrime = rgen.nextInt(LOWER_BOUND,UPPER_BOUND);
			boolean isPrimeQ = false;
			while (isPrimeQ = false) {
				for (int i=2; i<qPrime; i++) {
					int k=i;
					if (qPrime%k == 0) {
						isPrimeQ = true;
					}
				}
				if (isPrimeQ==true) {
					qPrime = rgen.nextInt(LOWER_BOUND,UPPER_BOUND);
					isPrimeQ=false;
				}else {
					isPrimeQ=true;
				}
			}
		}
		nValue = pPrime*qPrime;
		while (nValue >= NVALUE_UPPER_BOUND) {
			pPrime = rgen.nextInt(LOWER_BOUND,UPPER_BOUND);
			isPrimeP = false;
			while (isPrimeP = false) {
				for (int i=2; i<pPrime; i++) {
					int k=i;
					if (pPrime%k == 0) {
						isPrimeP = true;
					}
				}
				if (isPrimeP==true) {
					pPrime = rgen.nextInt(LOWER_BOUND,UPPER_BOUND);
					isPrimeP=false;
				}else {
					isPrimeP=true;
				}
			}
			qPrime = rgen.nextInt(LOWER_BOUND,UPPER_BOUND);
			boolean isPrimeQ;
			while (pPrime == qPrime) {
				qPrime = rgen.nextInt(LOWER_BOUND,UPPER_BOUND);
				isPrimeQ = false;
				while (isPrimeQ = false) {
					for (int i=2; i<qPrime; i++) {
						int k=i;
						if (qPrime%k == 0) {
							isPrimeQ = true;
						}
					}
					if (isPrimeQ==true) {
						qPrime = rgen.nextInt(LOWER_BOUND,UPPER_BOUND);
						isPrimeQ=false;
					}else {
						isPrimeQ=true;
					}
				}
			}
			nValue = pPrime*qPrime;
		}
		totient = (pPrime-1)*(qPrime-1);
		encryptionKey = rgen.nextInt(LOWER_BOUND,UPPER_BOUND);
		while (encryptionKey>nValue) {
			encryptionKey = rgen.nextInt(LOWER_BOUND,UPPER_BOUND);
		}
		while (gcd(encryptionKey, totient)!=1) {
			encryptionKey = rgen.nextInt(LOWER_BOUND,UPPER_BOUND);
			while (encryptionKey>nValue) {
				encryptionKey = rgen.nextInt(LOWER_BOUND,UPPER_BOUND);
			}
		}
		
		// your code ends here	
	}
	private int gcd(int a, int b) {
		for(int i=2; i<(a+1); i++) {
			if(a%i==0 && b%i==0) {
			return i;
			}
		}
		return 1;
	}
	/**
	 * This method produced the decryption key using
	 * the encryption key and assigns it to
	 * an instance variable of this class
	 */
	private void produceDecrptionKey() {
		//your code starts here
		decryptionKey = multInverse(encryptionKey,totient);
		// your code ends here
		
	}
	private int multInverse(int a, int b) {
		int answer = rgen.nextInt(0,b);
		while ((answer*a)%b != 1) {
			answer = rgen.nextInt(0,b);
		}
		return answer;
	}
	/**
	 * This method prompt user to enter a 5-digit
	 * integer and does the necessary checks for
	 * invalid user entry.
	 */
	private void getUserMessage() {
		//your code starts here
		message = readInt("Enter a 5-digit integer value ");
		while (message>99999 || message<10000) {
			println("Not a 5-digit integer value.");
			message = readInt("Please reenter a 5-digit integer value ");
		}
		// your code ends here
	}
	/**
	 * This method takes the modulo of two integers taking into consideration negative values as well
	 * @param num - number to be divided
	 * @param mod - divisor number
	 * @return integer value
	 */
	public int modulo(int num, int mod) {
		int answer = num%mod;
		//your code starts here
		if (answer<0) {
			answer += mod;
		}
		// your code ends here
		return answer;
	}
	/** 
	 * This method encrypt the integer message entered by the user
	 * by encrypting every digit in the integer one by one.
	 * It displays on the console the integer value before and after
	 * encryption.
	 * It decryptes every digit separately and display the original
	 * integer message on the console.
	 * 
	 */
	private void encryptDescrMessage() {
		//your code starts here
		encryptedMessage= ""+fifthEncryptedDigit+fourthEncryptedDigit+thirdEncryptedDigit+secondEncryptedDigit+firstEncryptedDigit+"";
		decryptedMessage= ""+fifthDecryptedDigit+fourthDecryptedDigit+thirdDecryptedDigit+secondDecryptedDigit+firstDecryptedDigit+"";
		println("The encrypted message is "+encryptedMessage);
		println("The decrypted message is "+decryptedMessage);
		

		// your code ends here
	}
	
	// Helper Methods
	//your code starts here
	private void encrypteDigits() {
		for(int i=0; i<5; i++) {
			double k = i;
			int y = (int)Math.pow(10, k);
			int x=(message-(modulo(message,y)))/y;
			currentDigit=modulo(x,10);
			currentEncryptedDigit=exptMod(currentDigit,encryptionKey,nValue);
			println("		encrypted digit " + currentDigit + " to " + currentEncryptedDigit + " decrypted digit");
			currentDecryptedDigit=exptMod(currentEncryptedDigit,decryptionKey,nValue);
			println("		decrypted digit " + currentEncryptedDigit + " to " + currentDecryptedDigit + " encrypted digit");
			if(i==1) {
				firstEncryptedDigit=currentEncryptedDigit;
				firstDecryptedDigit=currentDecryptedDigit;
			}else if(i==2) {
				secondEncryptedDigit=currentEncryptedDigit;
				secondDecryptedDigit=currentDecryptedDigit;
			}else if(i==3) {
				thirdEncryptedDigit=currentEncryptedDigit;
				thirdDecryptedDigit=currentDecryptedDigit;
			}else if(i==4) {
				fourthEncryptedDigit=currentEncryptedDigit;
				fourthDecryptedDigit=currentDecryptedDigit;
			}else if(i==5) {
				fifthEncryptedDigit=currentEncryptedDigit;
				fifthDecryptedDigit=currentDecryptedDigit;
			}
			
			
		}
	}

	// your code ends here
	
	
	// Additional Variables and Constants
	//your code starts here
	private static int fifthEncryptedDigit = 1;
	private static int fourthEncryptedDigit = 1;
	private static int thirdEncryptedDigit = 1;
	private static int secondEncryptedDigit = 1;
	private static int firstEncryptedDigit = 1;
	private static int fifthDecryptedDigit = 1;
	private static int fourthDecryptedDigit = 1;
	private static int thirdDecryptedDigit = 1;
	private static int secondDecryptedDigit = 1;
	private static int firstDecryptedDigit = 1;

	// your code ends here
	
	///////////////DO NOT CHANGE CODE BELOW THIS LINE //////////////////////////////////////////////////////
	/** 
	 * This method creates exponentiation of base over power performed over a modulus.
	 * @param base - base number to be exponentially increased
	 * @param power - power number 
	 * @param mod - modulus
	 * @return integer value
	 */
	private int exptMod(int base, int power, int mod) {

		int result = 1;
		for (int i = 0; i < power; i++) {
			result = modulo(result * base, mod);
		}
		return result;
	}
	
	// Instance Variables
	private static int nValue = -1;					// nValue of p and q
	private static int message = -1;					// message to be encrypted
	private static String encryptedMessage = "";		// message encrypted
	private static String decryptedMessage = "";		// message encrypted
	private static int pPrime = -1;					// First prime number used in rsa algorithm
	private static int qPrime = -1;					// Second prime number used in rsa algorithm
	private static int totient = -1;					// totient value of p and q
	private static int encryptionKey = -1;			// public key used for encryption
	private static int decryptionKey = -1;			// private key used for decryption
	private static int currentDigit = -1;			//message content will be encrypted
	private static int currentEncryptedDigit = -1; 	//message content currently being encrypted
	private static int currentDecryptedDigit = -1; 	//message content decrypted
	
	// Constants Variables
	private RandomGenerator rgen = RandomGenerator.getInstance(); // an instance of Random Generator
	private static final int NVALUE_UPPER_BOUND = 46340;			//maximum number for a mod 
	private static final int UPPER_BOUND  = 1000; 				//maximum number for a prime and key number	
	private static final int LOWER_BOUND = 3;					//smallest number for a prime and key number	
	private static final int MESSAGE_LENGTH = 5;					//length of the message id

}
