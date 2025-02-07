import { useState, useEffect } from "react";

const useFilterSortPagination = (data, plansPerPage = 10) => {
  const [filteredData, setFilteredData] = useState([]);
  const [sortOption, setSortOption] = useState("");
  const [selectedFilter, setSelectedFilter] = useState("");
  const [currentPage, setCurrentPage] = useState(1);

  useEffect(() => {
    setFilteredData(data);
  }, [data]);

  // Filter by Price Range
  const filterByPriceRange = (range) => {
    if (selectedFilter === range) return; // Prevent redundant API calls

    setSelectedFilter(range);
    let url = "http://localhost:8103/api/vendor-subscription-plans/filter/";

    if (range === "0-1000") url += "0-1000";
    else if (range === "1000-5000") url += "1000-5000";
    else if (range === "5000+") url += "5000+";
    else {
      setFilteredData(data);
      return;
    }

    fetch(url)
      .then((response) => response.json())
      .then((filtered) => {
        setFilteredData(filtered);
        setCurrentPage(1); // Reset pagination
      })
      .catch((error) => console.error("Error filtering:", error));
  };

  // Sort Data
  const sortData = (order) => {
    if (sortOption === order) return; // Prevent redundant sorting

    setSortOption(order);
    setFilteredData((prevData) => {
      let sortedData = [...prevData];

      if (order === "lowToHigh") sortedData.sort((a, b) => a.price - b.price);
      if (order === "highToLow") sortedData.sort((a, b) => b.price - a.price);

      return sortedData;
    });
  };

  // Pagination Logic
  const indexOfLastItem = currentPage * plansPerPage;
  const indexOfFirstItem = indexOfLastItem - plansPerPage;
  const currentItems = filteredData.slice(indexOfFirstItem, indexOfLastItem);
  const totalPages = Math.ceil(filteredData.length / plansPerPage);

  return {
    filteredData,
    currentItems,
    currentPage,
    totalPages,
    sortOption,
    selectedFilter,
    setCurrentPage,
    filterByPriceRange,
    sortData,
  };
};

export default useFilterSortPagination;
