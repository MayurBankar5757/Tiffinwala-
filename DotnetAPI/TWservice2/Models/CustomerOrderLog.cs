using System;
using System.Collections.Generic;

namespace TWservice2.Models
{
    public partial class CustomerOrderLog
    {
        public CustomerOrderLog()
        {
            Payments = new HashSet<Payment>();
        }

        public int OrderId { get; set; }
        public int Uid { get; set; }
        public DateTime OrderDate { get; set; }
        public int Quantity { get; set; }

        public virtual User UidNavigation { get; set; } = null!;
        public virtual ICollection<Payment> Payments { get; set; }
    }
}
