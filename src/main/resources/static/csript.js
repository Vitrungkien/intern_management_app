var currentPage = 1;
var internsPerPage = 5;
var interns = [
    { name: "Intern 1", address: "Địa chỉ 1", email: "intern1@example.com" },
    // Thêm dữ liệu intern khác vào đây
];

function displayInterns() {
    var table = document.getElementById("internTable");
    var start = (currentPage - 1) * internsPerPage;
    var end = start + internsPerPage;
    var paginatedInterns = interns.slice(start, end);

    table.innerHTML = "<tr><th>id</th><th>Name</th><th>Position</th><th>Phone number</th><th>Email</th><th>Address</th></tr>";

    for (var i = 0; i < paginatedInterns.length; i++) {
        var intern = paginatedInterns[i];
        var row = `<tr><td>${intern.name}</td><td>${intern.address}</td><td>${intern.email}</td></tr>`;
        table.innerHTML += row;
    }
}

function previousPage() {
    if (currentPage > 1) {
        currentPage--;
        displayInterns();
    }
}

function nextPage() {
    var totalPages = Math.ceil(interns.length / internsPerPage);
    if (currentPage < totalPages) {
        currentPage++;
        displayInterns();
    }
}

document.getElementById("searchInput").addEventListener("input", function() {
    var input = this.value.toLowerCase();
    var filteredInterns = interns.filter(function(intern) {
        return (
            intern.name.toLowerCase().includes(input) ||
            intern.address.toLowerCase().includes(input) ||
            intern.email.toLowerCase().includes(input)
        );
    });

    currentPage = 1;
    interns = filteredInterns;
    displayInterns();
});

displayInterns();
