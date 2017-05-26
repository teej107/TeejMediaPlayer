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
            library.sort((a, b) =>
            {
                if(a[property] > b[property])
                    return 1;
                if(a[property] < b[property])
                    return -1;

                if(property === 'title')
                {

                }
                return 0;
            });
        }
    }
}

export default SongSort