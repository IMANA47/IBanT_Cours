#include<16F877.h>
#use delay(clock=20M)
#fuses HS

main(){
while(1){
output_high(pin_B0);
delay_ms(500);
output_low(pin_B0);
delay_ms(500);
}
}
