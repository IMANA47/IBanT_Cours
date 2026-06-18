int R1=13;
float VIN=A0;
int N;
void setup() {
  // put your setup code here, to run once:
pinMode(R1,OUTPUT);
pinMode(VIN,INPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
VIN= 5*N/1023;
N= analogRead(A0);
if (VIN > 1.5);
{digitalWrite(R1,HIGH);}
{digitalWrite(R1,LOW);}
}
