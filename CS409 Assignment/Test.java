public class Test {

    int Add(int num)
    {
        return num + num;
    }

    int Increment(int num)
    {
        return num++;
    }

    int Decrement(int num)
    {
        num--;
        num--;
        num--;
        num--;num--;num--;num--;num--;num--;
        num--;num--;num--;num--;num--;num--;num--;num--;num--;num--;num--;num--;num--;num--;num--;
        num--;num--;num--;num--;num--;num--;num--;num--;num--;num--;num--;num--;num--;
        return num--;
    }

    int Total()
    {
        int num = 3;

        return num.Add(num).Incement(num).Decrement(num);
    }
}
