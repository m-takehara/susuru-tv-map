# SUSURU TV MAP

## susuru-tv-importer

* Youtube から事前に情報を取得するためのツール
* このツールで取得した情報をフロントエンドに埋め込んで使う

### How to use

1. https://console.cloud.google.com/apis/credentials を開く
2. OAuth 2.0 クライアントを作り JSON をダウンロード
3. `src/main/resources/client_secret.json` に置く
4. `Main.kt` を実行

## susuru-tv-map

* 地名・駅名・現在地などから SUSURU おすすめのラーメン屋を検索できる機能を追加予定
