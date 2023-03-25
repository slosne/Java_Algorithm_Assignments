import java.util.Stack;

public class StackIntro {

    public static Object returnElement(Stack stack){

        Object element1, element2, element3 = null;

        if (!stack.isEmpty())
        {
            element1 = stack.pop();
            if (!stack.isEmpty())
            {
                element2 = stack.pop();
                if (!stack.isEmpty())
                    element3 = stack.peek();
                stack.push(element2);
            }
            stack.push(element1);
        }

        return element3;
    }

    public static Object returnBottom(Stack stack){
        Stack<Object> helpStack = new Stack<>();
        Object b;

        if (stack.isEmpty()){
            return null;
        }
        while(!stack.isEmpty()){
            helpStack.push(stack.pop());
        }

        b = helpStack.peek();

        while(!helpStack.isEmpty()){
            stack.push(helpStack.pop());
        }
        return b;
    }

    public static Stack removeX(Stack stack, Integer X){
        Stack<Integer> hjelpeStack = new Stack<>();
        Integer I;

        //Så lenge hovedstack ikke er tom - I blir det elementet man popper av hovedstacken.
        //Dersom I ikke er lik X, så skal elementet legges på hjelpestacken
        while(!stack.isEmpty()){
            I = (Integer) stack.pop();
            if (!I.equals(X)){
                hjelpeStack.push(I);
            }
        }
        //Hjelpestacken består nå av elementer fra hovedstack, med unntak av alle X'er.
        //Fra hjelpestacken popper man av alle elementer og legger disse tilbake på hovedstacken med push.
        while(!hjelpeStack.isEmpty()){
            stack.push(hjelpeStack.pop());
        }
        return stack;
    }

    public static Stack skrivStack(Stack stack){
        if(!stack.isEmpty()){
            Object X = stack.pop();
            System.out.println(X+ " ");
            skrivStack(stack);
            stack.push(X);
        }
        return stack;
    }

    public static void main(String[] args) {

        Stack<Integer> stack1 = new Stack<>();
        stack1.push(55);
        stack1.push(25);
        stack1.push(33);
        stack1.push(87);
        stack1.push(55);
        stack1.push(44);
        stack1.push(21);
        stack1.push(55);

        System.out.println(stack1);

        System.out.println(returnElement(stack1));
        System.out.println(stack1);

        System.out.println("\n\n2.metode:\n");
        System.out.println(returnBottom(stack1));

        System.out.println("\nMetode 3:\n");
        System.out.println(removeX(stack1, 55));

        System.out.println("\nMetode 4:\n");
        System.out.println(skrivStack(stack1));


    }

}