clear
reset
set key top left outside horizontal autotitle columnhead
# Make the x axis labels easier to read.
set xtics rotate out
# Select histogram data
set style data histogram
# Give the bars a plain fill pattern, and draw a solid line around them.
set style fill solid border
set style histogram rowstacked
set boxwidth 0.6 relative
plot for [COL=2:4] 'test_appbar.dat' using COL:xticlabels(1) title columnheader
