import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Project {

    public static List<String> validInputs = new ArrayList<>(Arrays.asList("U","D", "R", "L", "P", "C", "Q"));
    public static int getDirection(int currentDirection, String currentMove) {
        if (currentMove.equalsIgnoreCase("R")) {
            if (currentDirection == 1) {
                return 4;
            } else if (currentDirection == 2) {
                return 3;
            } else if (currentDirection == 3) {
                return 1;
            } else if (currentDirection == 4) {
                return 2;
            }
        } else {
            if (currentDirection == 1) {
                return 3;
            } else if (currentDirection == 2) {
                return 4;
            } else if (currentDirection == 3) {
                return 2;
            } else if (currentDirection == 4) {
                return 1;
            }
        }
        return 1;
    }

    public static int[] getCoordinates(int[][] outputArray, int x, int y, String currentMove, int currentDirection, int penState) {
        if (currentMove.equalsIgnoreCase("U")) {
            return new int[]{x, y, currentDirection, 0};
        } else if (currentMove.equalsIgnoreCase("D")) {
            outputArray[x][y] = 1;
            return new int[]{x, y, currentDirection, 1};
        } else if (currentMove.equalsIgnoreCase("R")) {
            return new int[]{x, y, Project.getDirection(currentDirection, currentMove), penState};
        } else if (currentMove.equalsIgnoreCase("L")) {
            return new int[]{x, y, Project.getDirection(currentDirection, currentMove), penState};
        } else if (currentMove.length() > 1) {
            if (currentMove.charAt(0) == 'M' || currentMove.charAt(0) == 'm') {
                int numberOfSpaces = Integer.valueOf(currentMove.substring(2));
                if (currentDirection == 1) {
                    if (penState == 1) {
                        Project.draw(outputArray, currentDirection, numberOfSpaces, x, y);
                    }
                    return new int[]{x + numberOfSpaces, y, currentDirection, penState};
                } else if (currentDirection == 2) {
                    if (penState == 1) {
                        Project.draw(outputArray, currentDirection, numberOfSpaces, x, y);
                    }
                    return new int[]{x - numberOfSpaces, y, currentDirection, penState};
                } else if (currentDirection == 3) {
                    if (penState == 1) {
                        Project.draw(outputArray, currentDirection, numberOfSpaces, x, y);
                    }
                    return new int[]{x, y - numberOfSpaces, currentDirection, penState};
                } else if (currentDirection == 4) {
                    if (penState == 1) {
                        Project.draw(outputArray, currentDirection, numberOfSpaces, x, y);
                    }
                    return new int[]{x, y + numberOfSpaces, currentDirection, penState};
                }

            }
        }
        return new int[0];
    }

    public static void draw(int[][] outputArray, int currentDirection, int numberOfSpaces, int x, int y) {
        if (currentDirection == 1) {
            for (int index = x + 1; index <= (x + numberOfSpaces); index++) {
                outputArray[index][y] = 1;
            }
        } else if (currentDirection == 2) {
            for (int index = x - 1; index >= (x - numberOfSpaces); index--) {
                outputArray[index][y] = 1;
            }
        } else if (currentDirection == 3) {
            for (int index = y - 1; index >= (y - numberOfSpaces); index--) {
                outputArray[x][index] = 1;
            }
        } else if (currentDirection == 4) {
            for (int index = y + 1; index <= (y + numberOfSpaces); index++) {
                outputArray[x][index] = 1;
            }
        }
    }

    public static void printMyArray(int[][] arr) {
        int arrLen = arr.length;
        int[][] finalArray = new int[arrLen][arrLen];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (arr[i][j] == 1) {
                    finalArray[arrLen - 1 - i][j] = 1;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                sb.append((finalArray[i][j] == 1 ? "*":" ") + " ");
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    public static void recurse(int[][] outputArray, int x, int y, int currentDirection, int upOrDown, List<String> inputArray, int currentIndexOfInputArray) {
    	if(currentIndexOfInputArray < inputArray.size()) {
            String currentMove = inputArray.get(currentIndexOfInputArray);
            if (currentMove.length() > 1 && (currentMove.charAt(0) == 'I' || currentMove.charAt(0) == 'i')) {
                int sizeOf2DArray = Integer.valueOf(currentMove.substring(2));
                outputArray = new int[sizeOf2DArray][sizeOf2DArray];
                recurse(outputArray, 0, 0, 1, 0, inputArray, currentIndexOfInputArray + 1);
            } else if (currentMove.equalsIgnoreCase("Q")) {
                return;
            } else if (currentMove.equalsIgnoreCase("C")) {
                String dir;
                if (currentDirection == 1) {
                    dir = "north";
                } else if (currentDirection == 2) {
                    dir = "south";
                } else if (currentDirection == 3) {
                    dir = "west";
                } else dir = "east";
                String penState = upOrDown == 0 ? "up" : "down";
                System.out.println("Position: " + y + ", " + x + " - Pen: " + penState + " - Facing: " + dir);
                recurse(outputArray, x, y, currentDirection, upOrDown, inputArray, currentIndexOfInputArray + 1);
            } else if (currentMove.equalsIgnoreCase("P")) {
                printMyArray(outputArray);
                recurse(outputArray, x, y, currentDirection, upOrDown, inputArray, currentIndexOfInputArray + 1);
            } else {
                int result[] = getCoordinates(outputArray, x, y, currentMove, currentDirection, upOrDown);
                recurse(outputArray, result[0], result[1], result[2], result[3], inputArray, currentIndexOfInputArray + 1);
            }
    	}

    }

    public static boolean isInputValid(String input) {
        if (Project.validInputs.contains(input.toUpperCase()) || input.matches("^(M|m)\\s[0-9]+$") 
        		|| input.matches("^(I|i)\\s[0-9]+$")) {
            return true;
        }
        return false;
    }

    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter command: \r\n");
        String s = sc.nextLine();
        ArrayList<String> inputArr = new ArrayList<>();
        while (!s.equalsIgnoreCase("Q")) {
            if(!isInputValid(s)) {
                System.out.println("Invalid Input");
                return;
            }
            inputArr.add(s);
            System.out.print("Enter command: \r\n");
            s = sc.nextLine();
            
        }
        recurse(new int[0][0], 0, 0, 1, 0, inputArr, 0);
    }


}
