# beginning of multiplot
set multiplot

set size ratio 0.25
set timefmt "%H:%M"
set xdata time
set xrange ["8:30":"12:40"]
set format x "%H:%M"
set xrange ["8:30":"12:40"]

# the user plot
set style fill solid 1.0
# move plot up to allign
set origin 0,0.086
set ytics 1
unset xtics

plot "medientage_2014_user.dat" using 1:($2+$3+$4) with boxes lc rgb "green" t "child", \
	"" using 1:($2+$3) with boxes lc rgb "yellow" t "female", \
	"" using 1:2 with boxes lc rgb "red" t "male"

# app state plot

set xtics nomirror out
unset ytics
set style fill
set border 3
set yrange [0:6]
#move plot right and scale to allign
set origin 0.02,0
set size 0.98,1

plot "medientage_2014_app.dat" using 1:3 with boxes t "pong", "" using 1:4 with boxes t "menu", "" using 1:9 with boxes t "offline" 
# end of multiplot
unset multiplot