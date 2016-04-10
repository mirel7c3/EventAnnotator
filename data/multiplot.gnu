# beginning of multiplot
#################DATAFILES##############
#Medientage 2013
#user_datafile = "medientage_2013_user.dat"
#app_datafile = "medientage_2013_app.dat"
#Medientage 2014
user_datafile = "medientage_2014_user.dat"
app_datafile = "medientage_2014_app.dat"
########################################

set multiplot

set size ratio 0.25
set timefmt "%H:%M"
set xdata time
set format x "%H:%M"
#set xrange ["8:30":"15:10"]
set xrange ["11:25":"12:50"]

# the user plot
set style fill solid 1.0
# move plot up to allign
set origin 0,0.086
set ytics 1
unset xtics

plot user_datafile using 1:($2+$3+$4) with boxes lc rgb "green" t "child", \
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

plot app_datafile using 1:3 with boxes t "pong", "" using 1:4 with boxes t "menu", "" using 1:9 with boxes t "offline" 
plot user_datafile using 1:2 with boxes t "card", \
	"" using 1:4 with boxes t "menu", \
	"" using 1:5 with boxes t "airhockey", \
	"" using 1:6 with boxes t "touchtrails", \
	"" using 1:7 with boxes t "fluid", \
	"" using 1:9 with boxes t "offline" \
	"" using 1:10 with boxes t "physics" \
	"" using 1:11 with boxes t "earth" \

# end of multiplot
unset multiplot