using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Linq;
using System.Web;

namespace TDR.Models
{
    public class ActiveOutlet
    {
        public int Count { get; set; }
        public string RespondentType { get; set; }
        public string Status { get; set; }
    }
    public static class PivotHelper
    {
        public static DataTable ToDataTable<T>(this IList<T> data)
        {
            PropertyDescriptorCollection props =
                TypeDescriptor.GetProperties(typeof(T));
            DataTable table = new DataTable();
            for (int i = 0; i < props.Count; i++)
            {
                PropertyDescriptor prop = props[i];
                table.Columns.Add(prop.Name, prop.PropertyType);
            }
            object[] values = new object[props.Count];
            foreach (T item in data)
            {
                for (int i = 0; i < values.Length; i++)
                {
                    values[i] = props[i].GetValue(item);
                }
                table.Rows.Add(values);
            }
            return table;
        }
        public static DataTable Pivot(DataTable dataValues, string keyColumn, string pivotNameColumn, string pivotValueColumn)
        {
            DataTable tmp = new DataTable();
            DataRow r;
            string LastKey = "//dummy//";
            int i, pValIndex, pNameIndex;
            string s;
            bool FirstRow = true;

            pValIndex = dataValues.Columns[pivotValueColumn].Ordinal;
            pNameIndex = dataValues.Columns[pivotNameColumn].Ordinal;

            for (i = 0; i <= dataValues.Columns.Count - 1; i++)
            {
                if (i != pValIndex && i != pNameIndex)
                    tmp.Columns.Add(dataValues.Columns[i].ColumnName, dataValues.Columns[i].DataType);
            }

            r = tmp.NewRow();

            foreach (DataRow row1 in dataValues.Rows)
            {
                if (row1[keyColumn].ToString() != LastKey)
                {
                    if (!FirstRow)
                        tmp.Rows.Add(r);

                    r = tmp.NewRow();
                    FirstRow = false;

                    //loop thru fields of row1 and populate tmp table
                    for (i = 0; i <= row1.ItemArray.Length - 3; i++)
                        r[i] = row1[tmp.Columns[i].ToString()];

                    LastKey = row1[keyColumn].ToString();
                }

                s = row1[pNameIndex].ToString();

                if (!tmp.Columns.Contains(s))
                    tmp.Columns.Add(s, typeof(decimal));//dataValues.Columns[pNameIndex].DataType);
                r[s] = row1[pValIndex];
            }

            //add that final row to the datatable:
            tmp.Rows.Add(r);

            return tmp;
        }


        public static Dictionary<TFirstKey, Dictionary<TSecondKey, TValue>> Pivot<TSource, TFirstKey, TSecondKey, TValue>(this IEnumerable<TSource> source, Func<TSource, TFirstKey> firstKeySelector, Func<TSource, TSecondKey> secondKeySelector, Func<IEnumerable<TSource>, TValue> aggregate)
        {
            var retVal = new Dictionary<TFirstKey, Dictionary<TSecondKey, TValue>>();

            var l = source.ToLookup(firstKeySelector);
            foreach (var item in l)
            {
                var dict = new Dictionary<TSecondKey, TValue>();
                retVal.Add(item.Key, dict);
                var subdict = item.ToLookup(secondKeySelector);
                foreach (var subitem in subdict)
                {
                    dict.Add(subitem.Key, aggregate(subitem));
                }
            }

            return retVal;
        }
    }
}