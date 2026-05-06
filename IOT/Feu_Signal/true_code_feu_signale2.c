#include <16F877A.h>

#fuses XT,NOWDT,NOPROTECT,NOLVP
#use delay(clock=8000000)

void main() {

   set_tris_d(0x00);
   output_d(0x00);

   while(TRUE) {

      // ====================
      // ETAPE 1
      // V1 ON
      // R2 ON
      // ====================

      output_bit(PIN_D0, 1);
      output_bit(PIN_D5, 1);

      delay_ms(5000);

      // ====================
      // ETAPE 2
      // ====================

      output_bit(PIN_D0, 1);
      output_bit(PIN_D4, 0);

      delay_ms(3000);

      // ====================
      // ETAPE 3
      // ====================

      output_bit(PIN_D4, 0);

      output_bit(PIN_D1, 0);
      output_bit(PIN_D3, 0);

      delay_ms(3000);

      // ====================
      // ETAPE 4
      // ====================

      output_bit(PIN_D1, 0);
      output_bit(PIN_D2, 0);

      delay_ms(5000);

      // ====================
      // ETAPE 5
      // ====================

      output_bit(PIN_D2, 0);
      output_bit(PIN_D3, 0);

      output_bit(PIN_D1, 0);
      output_bit(PIN_D4, 0);

      delay_ms(3000);

      output_bit(PIN_D1, 0);
      output_bit(PIN_D4, 0);
   }
}
