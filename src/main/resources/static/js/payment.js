let IMP = window.IMP;
function KG_pay() {
    IMP.init('imp04615367'); // IMP 객체 초기화

    IMP.request_pay({
        pg: "html5_inicis.INIpayTest", // 실제 계약 후에는 실제 PG사로 변경
        pay_method: 'card',
        merchant_uid: "order_no_0002", // 상점에서 관리하는 주문 번호를 전달
        name: '주문명:결제테스트',
        amount: 100,
        buyer_email: 'iamport@siot.do',
        buyer_name: '구매자이름',
        buyer_tel: '010-1234-5678',
        buyer_addr: '서울특별시 강남구 삼성동',
        buyer_postcode: '123-456',
    }, function (response) {
        // 결제 후 호출되는 callback 함수
        if (response.success) { // 결제 성공
            jQuery.ajax({
                url: "/api/v1/payment", // cross-domain error가 발생하지 않도록 주의
                type: 'POST',
                dataType: 'json',
                data: {
                    imp_uid: response.imp_uid
                    // 기타 필요한 데이터가 있으면 추가 전달
                }
            }).done(function (data) {
                // 서버에서 REST API로 결제 정보 확인 및 서비스 루틴이 정상인 경우
                let msg = '결제가 완료되었습니다.';
                msg += '\n고유ID : ' + response.imp_uid;
                msg += '\n상점 거래ID : ' + response.merchant_uid;
                msg += '\n결제 금액 : ' + response.amount;

                alert(msg);
            });
            console.log(response);
        } else {
            let msg = '결제에 실패하였습니다.';
            msg += '에러내용 : ' + response.error_msg;

            alert(msg);
        }
    });
}