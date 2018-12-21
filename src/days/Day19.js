var r0=1;

var r1=0;

var r2=0;

var r3=0;

var r4=0;

var r5=0;

 

r4+=2;

r4*=r4;

r4*=19;

r4*=11;

r3++;

r3*=22;

r3+=3;

r4+=r3;

if (r0 == 1) {

                r3=27;

                r3 *= 28;

                r3 += 29;

                r3 *= 30;

                r3 *= 14;

                r3 *= 32;

                r4 += r3;

                r0 = 0;

}

r5 = 1;

do {

                r2 = r4/r5;

                if (r2 % 1 === 0)

                                r0 = r5+r0;

                /*do {

                                r3 = r5*r2;

                                if (r3 == r4)

                                                r0 = r5+r0;

                                r2++;

                } while(r2 <= r4);*/

                r5++;

                //console.log(r0 + " " + r1 + " " + r2 + " " + r3 + " " + r4 + " " + r5);

} while ( r5 <= r4);

console.log(r0 + " " + r1 + " " + r2 + " " + r3 + " " + r4 + " " + r5);