int a;

int addThem(int d, int e) {
  int f;
  f = d + e;

  return f;
}

int main (void) {

  int b;
  int c;
  int g;
  int h;
  int i;

  b = 5;

  if (b == 5) {
    if (b==5) {
        a=200;
    }
    else {
        a=21;
    }
    a = 3;
  }
  else {
    a = 4;
  }

  g = 0;
  i = 1;
  while (i <= 8) {
    g = g + i;
    i = i+1;
    putchar (10);
    putchar (i+48);
  }
  putchar(10);

  h = g / 3;
  g = h * 4;

  c = addThem(a, b);
putchar (a+48);
a=7;
putchar (10);
putchar (a+48);
putchar (10);
putchar (b+48);
putchar (10);
putchar (c+48);
putchar (10);
  putchar (c+g);
  putchar (10);
 
  return 0;
}

