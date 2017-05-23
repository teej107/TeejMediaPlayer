/**
 * Created by tanner on 5/22/17.
 */
class SongSort
{
    constructor(name, callback)
    {
        this.getSortName = () => name;
        this.getCallback = () => callback;
    }

    static sortBy(property)
    {
        return function (library)
        {
            const newLib = {};
            Object.keys(library).sort((a, b) =>
            {
                a = library[a][property].toUpperCase();
                b = library[b][property].toUpperCase();
                if (a > b)
                    return 1;
                if (a < b)
                    return -1;
                return 0;
            }).forEach((e) =>
            {
                newLib[e] = library[e];
            });
            return newLib;
        }
    }
}

export default SongSort