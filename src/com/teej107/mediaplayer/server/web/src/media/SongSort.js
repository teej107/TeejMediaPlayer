/**
 * Created by tanner on 5/22/17.
 */
class SongSort
{
    static sortBy(property)
    {
        return function (library)
        {
            library.sort((a, b) =>
            {
                if(a[property] > b[property])
                    return 1;
                if(a[property] < b[property])
                    return -1;

                return 0;
            });
            return library;
        }
    }

    static viewBy(property)
    {
        return function (library)
        {
            var set = new Set();
            library.forEach((e) => set.add(e[property]));
            return Array.from(set).sort();
        }
    }
}

export default SongSort