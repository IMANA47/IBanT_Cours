int led= 13;
int led2= 12;
int led3 = 11;
int led4 = 9;
void setup() {
  // put your setup code here, to run once:
  pinMode(led, OUTPUT);
  pinMode(led2, OUTPUT);
  pinMode(led3, OUTPUT);
  
}

void loop() {
  // put your main code here, to run repeatedly:
digitalWrite(led, HIGH);
delay(500);
digitalWrite(led, LOW);
delay(500);

// Led 2
digitalWrite(led2, HIGH);
delay(500);
digitalWrite(led2, LOW);
delay(500);

//Led 3

digitalWrite(led3, HIGH);
delay(500);
digitalWrite(led3, LOW);
delay(500);

//Led 4

digitalWrite(led4, HIGH);
delay(500);
digitalWrite(led4, LOW);
delay(500);

}
