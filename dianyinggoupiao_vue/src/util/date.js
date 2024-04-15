export function formatDate(date) {
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0'); // 月份从0开始，需要加1，并确保两位数格式
    const day = date.getDate().toString().padStart(2, '0'); // 确保日期为两位数格式

    return `${year}-${month}-${day}`;
}
export function compareTime(oneDate,towDate){
    if (new Date(oneDate).getTime()>new Date(towDate).getTime()){
        return 1;
    }else if (new Date(oneDate).getTime()===new Date(towDate).getTime()) {
        return 0;
    }else if (new Date(oneDate).getTime()<new Date(towDate).getTime()) {
        return -1;
    }else {
        return -2;
    }
}