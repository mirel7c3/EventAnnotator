clear
reset
set key top left outside horizontal autotitle columnhead
# Make the x axis labels easier to read.
set xtics rotate out
# Select histogram data
#set style data histogram
# Give the bars a plain fill pattern, and draw a solid line around them.
set style fill solid 1.0
#set style histogram rowstacked
set size ratio 0.5
set timefmt "%H:%M"
set xdata time
set xrange ["8:30":"12:40"]
set format x "%H:%M"
#plot app state
plot "medientage_2014_app.dat" using 1:3 with boxes t "pong", "" using 1:4 with boxes t "menu", "" using 1:9 with boxes t "offline" 
#plot user
plot "medientage_2014_user.dat" using 1:($2+$3+$4) with boxes lc rgb "green" t "child", \
	"" using 1:($2+$3) with boxes lc rgb "yellow" t "female", \
	"" using 1:2 with boxes lc rgb "red" t "male"
