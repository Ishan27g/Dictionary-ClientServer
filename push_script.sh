cp -r /Users/ishan/Desktop/SEM1-2020/Software/Eclipse_workspace/DS-Ass1/ .
cd src/
rm -rf *.class
cd ..
git add src/ bin/
git commit -m "Working multi-Threaded server, tested with 2 clients"
git push origin master
