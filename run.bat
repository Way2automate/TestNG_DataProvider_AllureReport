mvn clean test -Dthreads=10 && allure generate -c -o target/reports/allure && allure open target/reports/allure