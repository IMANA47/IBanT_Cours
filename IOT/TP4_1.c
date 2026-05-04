#include<16F877.h>
#use delay(clock=10M)
#fuses HS

main(){
while(1){
output_B(0b00000001);
delay_ms(500);
output_B(0b00000010);
delay_ms(500);
output_B(0b00000100);
delay_ms(500);
output_B(0b00001000);
delay_ms(500);
}
}
