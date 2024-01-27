ターミナルとサーバーを接続してテキスト情報のやり取りを行えるクライアントプログラムをC言語で作成せよ．

* Socket API を用い，コマンドライン引数で指定されたホスト名とポート番号のサーバーにTCPで接続する．
* ホスト名の名前解決には getaddrinfo を用いる．
* 入出力には read(2), write(2) を用いる．
* write(2) は1回で出力が完了しないことも想定すること．また，そのような write(2) 呼び出しを関数として独立させて，他からは write(2) 直接ではなく，その関数を呼び出すようにする．

* サーバーと接続したソケットおよび標準入力の2つを select() で監視することが望ましい．(評価150%)
* もし標準入力から入力があったら，その内容をサーバーへ送信する．
* もしサーバーからデータを受信したら，それを標準出力へ出力する．
* どちらかが EOF になるか，エラーが発生するまで上記を繰り返す．
* サーバーとの接続を切断して終了する．