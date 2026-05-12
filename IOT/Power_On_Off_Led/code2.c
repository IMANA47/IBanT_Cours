#include <16F877A.h>
#use delay(clock=10M)
#fuses HS

int n;
main(){
set_tris_A(0b00000001);
set_tris_B(0b00000000);

while(1){

n = input(PIN_A0);

if(n==1)
{
output_high(PIN_B0);
delay_ms(500);
output_low(PIN_B0);
delay_ms(500);
}
else
{
output_B(0);
}
}
}
