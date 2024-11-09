// 모달 열기 함수
function openModal(element) {
    const videoUrl = element.getAttribute("data-video-url");
    const videoId = element.getAttribute("data-video-id");
    window.selectedVideoId = videoId;
    document.getElementById("placeSearchModal").style.display = "block";
    const videoIframe = document.getElementById("videoIframe");
    videoIframe.src = `https://www.youtube.com/embed/${videoUrl}`;
}

// 모달 닫기 함수
function closeModal() {
    document.getElementById("placeSearchModal").style.display = "none";
    document.getElementById("videoIframe").src = ""; // 비디오 정지
}

// 모달 외부 클릭 시 닫기
window.onclick = function (event) {
    const modal = document.getElementById("placeSearchModal");
    if (event.target === modal) {
        closeModal();
    }
};

function searchPlaces() {

    const keyword = document.getElementById('keyword').value;
    if (!keyword.trim()) {
        alert("검색어를 입력하세요.");
        return;
    }

    const ps = new kakao.maps.services.Places();
    // Kakao Maps API를 통해 장소 검색
    ps.keywordSearch(keyword, function (data, status) {
        const tbody = $('#search-tbody');
        tbody.empty();

        if (status === kakao.maps.services.Status.OK) {
            data.forEach(place => {
                const row = `
                    <tr>
                        <td>${place.place_name}</td>
                        <td>${place.address_name}</td>
                        <td><button onclick="registerPlace(${place.id})">등록</button></td>
                    </tr>`;
                tbody.append(row);
            });
        } else {
            alert("장소 검색에 실패했습니다.");
        }
    });
}

// 장소 등록 및 API 호출
function registerPlace(placeId) {
    const videoId = window.selectedVideoId;
    $.ajax({
        url: `/crawling/video/${videoId}/place/${placeId}`,
        method: 'POST',
        success: function () {
            alert("장소가 등록되었습니다.");
            closeModal();
            location.reload();
        },
        error: function () {
            alert("장소 등록에 실패했습니다.");
        }
    });
}

function deleteVideo(element) {
    if (!confirm("정말로 이 영상을 삭제하시겠습니까?")) {
        return;
    }
    const videoId = element.getAttribute("data-video-id");
    $.ajax({
        url: `/videos/${videoId}`,
        method: 'DELETE',
        success: function () {
            alert("장소가 삭제되었습니다.");
            location.reload()
        },
        error: function () {
            alert("장소 삭제에 실패했습니다.");
        }
    });
}