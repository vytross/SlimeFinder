import java.lang.*;
import java.util.Scanner;
public class slimefinder {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		final long psWorldSeed = 0x81CA00A4A271B183L; //protosky seed converted to hex
		String input = scan.nextLine();
		long seedInput = input.equalsIgnoreCase("ps") ? psWorldSeed : Long.parseLong(input); //allows input of ps for protosky world seed
		int slimeMax = 0;
		int slimeChunkNum;
		int xtemp, ztemp;
		for (int n = 0; n < 1875000; n++) { //checks in a square of side length 2n around 0, 0
			for (int x = (-1 * n); x <= n; x++) {
				slimeMax = slimeDisplay(seedInput, x, -n, slimeMax);
				slimeMax = slimeDisplay(seedInput, x, n, slimeMax);
			}	
			for (int z = (-1 * n); z <= n; z++) {
				slimeMax = slimeDisplay(seedInput, -n, z, slimeMax);
				slimeMax = slimeDisplay(seedInput, n, z, slimeMax);
			}
			if ((n % 625) == 0)
				System.out.println("Search has reached " + (n * 16) + " blocks from 0, 0"); //checks every 10k blocks
		}
    }

	public static int checkSlime(long seed, int xPosition, int zPosition, int slimes) { //the gross math part that i spent too long on
		return (((int)((((((seed + (int)(xPosition * xPosition * 0x4C1906) + (int)(xPosition * 0x5AC0DB) + (int)(zPosition * zPosition) * 0x4307A7L + (int)(zPosition * 0x5F24F) ^ 0x3AD8025F) ^ 0x5DEECE66DL) & ((1L << 48) - 1)) * 0x5DEECE66DL) & ((1L << 48) - 1)) >>> 17) % 10) == 0 ? (slimes + 1) : slimes);
	}
	public static int slimeDisplay(long worldseed, int x, int z, int slimeChunkMax) {
		int slimeChunkNum = 0;
		int xtemp, ztemp;
		for (xtemp = -7; xtemp <= 8; xtemp++) { //making the circle to check for slime chunks in, next version will probably do this more efficiently
			switch (xtemp) {
				case -7:
				case 8:
					for (ztemp = 0; ztemp <= 1; ztemp++)
						slimeChunkNum = checkSlime(worldseed, (x + xtemp), (z + ztemp), slimeChunkNum);
					break;
				case -6:
				case -5:
				case -4:
				case -3:
					for (ztemp = (-9 - xtemp); ztemp <= (10 + xtemp); ztemp++)
						slimeChunkNum = checkSlime(worldseed, (x + xtemp), (z + ztemp), slimeChunkNum);
					break;
				case -2:
				case -1:
				case 2:
				case 3:
					for (ztemp = -6; ztemp <= 7; ztemp++)
						slimeChunkNum = checkSlime(worldseed, (x + xtemp), (z + ztemp), slimeChunkNum);
					break;
				case 0:
				case 1:
					for (ztemp = -7; ztemp <= 8; ztemp++)
						slimeChunkNum = checkSlime(worldseed, (x + xtemp), (z + ztemp), slimeChunkNum);
					break;
				case 4:
				case 5:
				case 6:
				case 7:
					for (ztemp = (xtemp - 10); ztemp <= (11 - xtemp); ztemp++)
						slimeChunkNum = checkSlime(worldseed, (x + xtemp), (z + ztemp), slimeChunkNum);
					break;
			}
		}
		if (slimeChunkNum > slimeChunkMax) { //checking output
			System.out.println(slimeChunkNum + " chunks found at x " + ((x + 1) * 16) + ", z " + ((z + 1) * 16));
			slimeChunkMax = slimeChunkNum;
		}
		return slimeChunkMax;
	}	
}